package com.nelu.quran_api.service

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Build
import android.os.Bundle
import androidx.annotation.OptIn
import androidx.core.app.NotificationCompat
import androidx.core.os.bundleOf
import androidx.media3.common.MediaItem
import androidx.media3.exoplayer.ExoPlayer
import androidx.media3.exoplayer.source.ConcatenatingMediaSource
import androidx.media3.exoplayer.source.ProgressiveMediaSource
import androidx.media3.session.MediaSession
import androidx.media3.session.MediaSessionService
import androidx.media3.common.util.UnstableApi
import androidx.media3.datasource.DefaultDataSource
import androidx.media.app.NotificationCompat.MediaStyle
import androidx.media3.common.util.Log
import kotlinx.coroutines.*
import java.io.File
import java.net.URL

@OptIn(UnstableApi::class)
class AudioService : MediaSessionService() {

    private var foreground = false

    private val player: ExoPlayer by lazy {
        ExoPlayer.Builder(this).build()
    }

    private val mediaSession: MediaSession by lazy {
        MediaSession.Builder(this, player).build()
    }

    private val concatenatingMediaSource by lazy {
        ConcatenatingMediaSource()
    }

    private val job = Job()
    private val scope = CoroutineScope(Dispatchers.IO + job)

    fun play(start: Int, count: Int) {
        player.pause()
        player.clearMediaItems()
        concatenatingMediaSource.clear()

        repeat(count) { i->
            addMediaItemWithBackgroundDownload(
                "https://cdn.islamic.network/quran/audio/64/ar.alafasy/${i+start}.mp3"
            )
        }

        player.setMediaSource(concatenatingMediaSource)
        player.prepare()
        player.play()

        if (foreground.not()) {
            Log.e("Foreground", "Triggered.")
            startForegroundService()
        }
    }

    override fun onCreate() {
        super.onCreate()
        audioService = this
    }

    private fun addMediaItemWithBackgroundDownload(url: String) {
        concatenatingMediaSource.addMediaSource(
            ProgressiveMediaSource.Factory(
                DefaultDataSource.Factory(this)
            ).createMediaSource(
                checkFile(url)?.let { localPath->
                    MediaItem.fromUri(localPath)
                } ?: run {
                    scope.launch {
                        downloadFile(url)?.let { localPath->
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
                    MediaItem.fromUri(url)
                }
            )
        )
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
        foreground = true
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
                    androidx.media3.session.R.drawable.media3_icon_pause, "Pause", getActionIntent(ACTION_PAUSE)
                )
            )
            .addAction(
                NotificationCompat.Action(
                    androidx.media3.session.R.drawable.media3_icon_play, "Play", getActionIntent(ACTION_PLAY)
                )
            )
            .addAction(
                NotificationCompat.Action(
                    androidx.media3.session.R.drawable.media3_icon_stop, "Stop", getActionIntent(ACTION_STOP)
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
        foreground = false
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
        const val NOTIFICATION_ID = 13001
        const val ACTION_PLAY = "com.nelu.quran_api.service.ACTION_PLAY"
        const val ACTION_PAUSE = "com.nelu.quran_api.service.ACTION_PAUSE"
        const val ACTION_STOP = "com.nelu.quran_api.service.ACTION_STOP"

        lateinit var audioService: AudioService
    }
}