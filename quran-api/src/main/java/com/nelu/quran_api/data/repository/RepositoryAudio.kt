package com.nelu.quran_api.data.repository

import android.app.Application
import android.os.Bundle
import android.support.v4.media.session.PlaybackStateCompat
import android.util.Log
import androidx.lifecycle.LiveData
import com.nelu.quran_api.data.repository.base.BaseAudio
import com.nelu.quran_api.di.QuranAPI
import com.nelu.quran_api.service.AudioController

class RepositoryAudio(
    val application: Application
) : AudioController(application), BaseAudio {

    override fun playAyah(ayah: Int, qari: String) {
        mediaController.transportControls.play()
    }

    override fun playSurah(surah: Int, qari: String) {
        TODO("Not yet implemented")
    }

    override fun playJuz(juz: Int, qari: String) {
        TODO("Not yet implemented")
    }

    override fun playPage(page: Int, qari: String) {
        TODO("Not yet implemented")
    }

    override fun pause() {
        mediaController.transportControls.pause()
    }

    override fun resume(): Boolean {
        if (mediaController.playbackState?.state == PlaybackStateCompat.STATE_PAUSED) {
            mediaController.transportControls.play()
            return true
        }
        return false
    }

    override fun stop() {
        mediaController.transportControls.stop()
    }

    override fun getQaris(): List<String> {
        TODO("Not yet implemented")
    }

    override fun getPlaybackState(): LiveData<PlaybackStateCompat?> {
        return playbackState
    }
}