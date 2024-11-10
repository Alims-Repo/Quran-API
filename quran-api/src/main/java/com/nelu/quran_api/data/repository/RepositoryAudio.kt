package com.nelu.quran_api.data.repository

import android.app.Application
import android.content.ComponentName
import android.content.Intent
import android.support.v4.media.MediaBrowserCompat
import android.support.v4.media.session.MediaControllerCompat
import android.support.v4.media.session.PlaybackStateCompat
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.nelu.quran_api.data.repository.base.BaseAudio
import com.nelu.quran_api.service.AudioController
import com.nelu.quran_api.service.AudioService

class RepositoryAudio(
    val application: Application
) : AudioController(application), BaseAudio {

    override fun playAyah(ayah: Int) {
        mediaController.transportControls.play()
    }

    override fun playSurah(surah: Int) {
        TODO("Not yet implemented")
    }

    override fun playJuz(juz: Int) {
        TODO("Not yet implemented")
    }

    override fun playPage(page: Int) {
        TODO("Not yet implemented")
    }

    override fun pause() {
        mediaController.transportControls.pause()
    }

    override fun resume(): Boolean {
        TODO("Not yet implemented")
    }

    override fun stop() {
        TODO("Not yet implemented")
    }

    override fun getPlaybackState(): LiveData<PlaybackStateCompat?> {
        return playbackState
    }
}