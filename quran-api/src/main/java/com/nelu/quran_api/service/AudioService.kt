package com.nelu.quran_api.service

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Build
import androidx.annotation.OptIn
import androidx.core.app.NotificationCompat
import androidx.media3.common.MediaItem
import androidx.media3.exoplayer.ExoPlayer
import androidx.media3.exoplayer.source.ConcatenatingMediaSource
import androidx.media3.exoplayer.source.ProgressiveMediaSource
import androidx.media3.session.MediaSession
import androidx.media3.session.MediaSessionService
import androidx.media3.common.util.UnstableApi
import androidx.media3.datasource.DefaultDataSource
import androidx.media.app.NotificationCompat.MediaStyle
import kotlinx.coroutines.*
import java.io.File
import java.net.URL

@OptIn(UnstableApi::class)
class AudioService : MediaSessionService() {

    private lateinit var player: ExoPlayer
    private lateinit var mediaSession: MediaSession
    private lateinit var concatenatingMediaSource: ConcatenatingMediaSource

    private val job = Job()
    private val scope = CoroutineScope(Dispatchers.IO + job)

    override fun onCreate() {
        super.onCreate()

        // Initialize ExoPlayer and MediaSession
        player = ExoPlayer.Builder(this).build()
        mediaSession = MediaSession.Builder(this, player).build()

        concatenatingMediaSource = ConcatenatingMediaSource()

        repeat(10) { i->
            addMediaItemWithBackgroundDownload(
                "https://cdn.islamic.network/quran/audio/64/ar.alafasy/${i+1}.mp3"
            )
        }

        player.setMediaSource(concatenatingMediaSource)
        player.prepare()
        player.play()

        startForegroundService()
    }

    private fun addMediaItemWithBackgroundDownload(url: String) {
        checkFile(url)?.let { localPath->
            concatenatingMediaSource.addMediaSource(
                ProgressiveMediaSource.Factory(DefaultDataSource.Factory(this@AudioService))
                    .createMediaSource(
                        MediaItem.fromUri(localPath)
                    )
            )
        } ?: run {
            val mediaItem = MediaItem.fromUri(url)
            val mediaSource = ProgressiveMediaSource.Factory(DefaultDataSource.Factory(this))
                .createMediaSource(mediaItem)

            concatenatingMediaSource.addMediaSource(mediaSource)

            scope.launch {
                val localPath = downloadFile(url)
                if (localPath != null) {
                    val index = concatenatingMediaSource.size - 1
                    val localMediaItem = MediaItem.fromUri(localPath)
                    val localMediaSource = ProgressiveMediaSource.Factory(DefaultDataSource.Factory(this@AudioService))
                        .createMediaSource(localMediaItem)
                    withContext(Dispatchers.Main) {
                        concatenatingMediaSource.removeMediaSource(index)
                        concatenatingMediaSource.addMediaSource(index, localMediaSource)
                    }
                }
            }
        }
    }

    private fun checkFile(url: String) : Uri? {
        val fileName = Uri.parse(url).lastPathSegment ?: return null
        val file = File(applicationContext.filesDir, "audio/al_afasy/$fileName")
        return if (file.exists()) Uri.fromFile(file) else null
    }

    // Download file and return local path
    private suspend fun downloadFile(url: String): Uri? = withContext(Dispatchers.IO) {
        try {
            val file = File(
                applicationContext.filesDir,
                "audio/al_afasy/${Uri.parse(url).lastPathSegment 
                    ?: return@withContext null}"
            )

            file.parentFile?.mkdirs()
            file.writeBytes(URL(url).readBytes())

            return@withContext Uri.fromFile(file)
        } catch (e: Exception) {
            e.printStackTrace()
            null
        }
    }

    private fun startForegroundService() {
        createNotificationChannel()

        val notificationIntent = Intent(this, AudioService::class.java)
        val pendingIntent = PendingIntent.getActivity(
            this, 0, notificationIntent,
            PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
        )

        val notification = NotificationCompat.Builder(this, CHANNEL_ID)
            .setContentTitle("Playing Audio")
            .setContentText("Your audio is playing")
            .setSmallIcon(androidx.media3.session.R.drawable.media3_notification_small_icon)
            .setContentIntent(pendingIntent)
            .setStyle(
                MediaStyle()
                    .setMediaSession(mediaSession.sessionCompatToken)
                    .setShowActionsInCompactView(0, 1, 2)
            )
            .addAction(
                NotificationCompat.Action(
                    androidx.media3.session.R.drawable.media3_notification_pause, "Pause", getActionIntent(ACTION_PAUSE)
                )
            )
            .addAction(
                NotificationCompat.Action(
                    androidx.media3.session.R.drawable.media3_notification_play, "Play", getActionIntent(ACTION_PLAY)
                )
            )
            .addAction(
                NotificationCompat.Action(
                    androidx.media3.session.R.drawable.media3_notification_pause, "Stop", getActionIntent(ACTION_STOP)
                )
            )
            .build()

        startForeground(NOTIFICATION_ID, notification)
    }

    private fun getActionIntent(action: String): PendingIntent {
        val intent = Intent(this, AudioService::class.java).apply {
            this.action = action
        }
        return PendingIntent.getService(
            this, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
        )
    }

    private fun createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = NotificationChannel(
                CHANNEL_ID,
                "Audio Playback",
                NotificationManager.IMPORTANCE_LOW
            ).apply {
                description = "Quran Audio playback controls"
            }
            val notificationManager = getSystemService(NotificationManager::class.java)
            notificationManager.createNotificationChannel(channel)
        }
    }

    override fun onDestroy() {
        player.release()
        mediaSession.release()
        job.cancel()
        super.onDestroy()
    }

    override fun onGetSession(controllerInfo: MediaSession.ControllerInfo): MediaSession? {
        return mediaSession
    }

    companion object {
        const val CHANNEL_ID = "AudioServiceChannel"
        const val NOTIFICATION_ID = 1
        const val ACTION_PLAY = "com.nelu.quran_api.service.ACTION_PLAY"
        const val ACTION_PAUSE = "com.nelu.quran_api.service.ACTION_PAUSE"
        const val ACTION_STOP = "com.nelu.quran_api.service.ACTION_STOP"
    }
}