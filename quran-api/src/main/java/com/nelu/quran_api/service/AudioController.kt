package com.nelu.quran_api.service

import android.app.Application
import android.content.ComponentName
import android.support.v4.media.MediaBrowserCompat
import android.support.v4.media.session.MediaControllerCompat
import android.support.v4.media.session.PlaybackStateCompat
import androidx.lifecycle.MutableLiveData

open class AudioController(
    application: Application
) {

    private lateinit var mediaBrowser: MediaBrowserCompat

    protected lateinit var mediaController: MediaControllerCompat

    protected val playbackState = MutableLiveData<PlaybackStateCompat?>()

    private val controllerCallback = object : MediaControllerCompat.Callback() {
        override fun onPlaybackStateChanged(state: PlaybackStateCompat?) {
            playbackState.postValue(state)
        }
    }

    private val connectionCallback = object : MediaBrowserCompat.ConnectionCallback() {
        override fun onConnected() {
            mediaController = MediaControllerCompat(
                application,
                mediaBrowser.sessionToken
            ).apply {
                registerCallback(controllerCallback)
            }
        }

        override fun onConnectionSuspended() {
            // Handle connection suspension
        }

        override fun onConnectionFailed() {
            // Handle connection failure
        }
    }

    init {
        mediaBrowser = MediaBrowserCompat(
            application,
            ComponentName(application, AudioService::class.java),
            connectionCallback,
            null
        )
        mediaBrowser.connect()
    }
}