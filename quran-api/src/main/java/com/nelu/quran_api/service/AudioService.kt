package com.nelu.quran_api.service

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Intent
import android.os.Build
import androidx.annotation.OptIn
import androidx.core.app.NotificationCompat
import androidx.media3.common.MediaItem
import androidx.media3.exoplayer.ExoPlayer
import androidx.media3.session.MediaSession
import androidx.media3.session.MediaSessionService
import androidx.media.app.NotificationCompat.MediaStyle
import androidx.media3.common.util.UnstableApi
import androidx.media3.datasource.DefaultDataSourceFactory
import androidx.media3.exoplayer.source.ConcatenatingMediaSource
import androidx.media3.exoplayer.source.ProgressiveMediaSource
import com.nelu.quran_api.R

class AudioService : MediaSessionService() {

    private lateinit var mediaSession: MediaSession
    private lateinit var player: ExoPlayer

    @OptIn(UnstableApi::class)
    override fun onCreate() {
        super.onCreate()

        // Initialize ExoPlayer
        player = ExoPlayer.Builder(this).build()

        // Initialize MediaSession
        mediaSession = MediaSession.Builder(this, player).build()

        // Create a list of MediaItems for the playlist
//        val mediaItems = listOf(
//            MediaItem.fromUri("https://download.samplelib.com/mp3/sample-15s.mp3"),
//            MediaItem.fromUri("https://download.samplelib.com/mp3/sample-12s.mp3"),
//            MediaItem.fromUri("https://download.samplelib.com/mp3/sample-9s.mp3")
//        )

        val mediaItems = listOf(
            "https://cdn.islamic.network/quran/audio/64/ar.alafasy/1.mp3",
            "https://download.samplelib.com/mp3/sample-12s.mp3",
            "https://download.samplelib.com/mp3/sample-9s.mp3"
        )

        val concatenatingMediaSource = ConcatenatingMediaSource()

        repeat(10) { i->
            val mediaItem = MediaItem.fromUri("https://cdn.islamic.network/quran/audio/64/ar.alafasy/${i+1}.mp3")
            val mediaSource = ProgressiveMediaSource.Factory(DefaultDataSourceFactory(this))
                .createMediaSource(mediaItem)
            concatenatingMediaSource.addMediaSource(mediaSource)
        }
//
//        // Add each media item to the concatenating media source
//        for (url in mediaItems) {
//            val mediaItem = MediaItem.fromUri(url)
//            val mediaSource = ProgressiveMediaSource.Factory(DefaultDataSourceFactory(this))
//                .createMediaSource(mediaItem)
//            concatenatingMediaSource.addMediaSource(mediaSource)
//        }

        // Set the concatenated media source to ExoPlayer
        player.setMediaSource(concatenatingMediaSource)





        // Add media items to the player
//        player.setMediaItems(mediaItems)
        player.prepare()

        // Start playback
        player.play()

        // Start foreground service with notification
        startForegroundService()
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
            .setSmallIcon(androidx.media3.session.R.drawable.media3_notification_small_icon) // Replace with your icon
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
                    androidx.media3.session.R.drawable.media3_notification_seek_to_previous, "Stop", getActionIntent(ACTION_STOP)
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
                description = "Audio playback controls"
            }
            val notificationManager = getSystemService(NotificationManager::class.java)
            notificationManager.createNotificationChannel(channel)
        }
    }

    override fun onDestroy() {
        player.release()
        mediaSession.release()
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