package com.nelu.quran_api.service

import android.app.Application
import android.content.ComponentName
import android.content.Context
import android.support.v4.media.MediaBrowserCompat
import android.support.v4.media.session.MediaControllerCompat
import android.support.v4.media.session.PlaybackStateCompat
import androidx.lifecycle.MutableLiveData

open class AudioController(
    context: Context
) {

    // Must have lateinit for mediaBrowser
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
                context,
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
            context,
            ComponentName(context, AudioService::class.java),
            connectionCallback,
            null
        )
        mediaBrowser.connect()
    }

    protected fun isReady() = ::mediaController.isInitialized
}