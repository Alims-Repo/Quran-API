package com.nelu.quran_api.data.repository

import android.app.Application
import android.os.Bundle
import android.support.v4.media.session.PlaybackStateCompat
import android.util.Log
import androidx.lifecycle.LiveData
import com.nelu.quran_api.data.db.dao.DaoQari
import com.nelu.quran_api.data.repository.base.BaseAudio
import com.nelu.quran_api.di.QuranAPI
import com.nelu.quran_api.service.AudioController
import com.nelu.quran_api.service.AudioService

class RepositoryAudio(
    private val daoQari: DaoQari
) : AudioController(daoQari.context), BaseAudio {

    override fun playAyah(ayah: Int, qari: String) {
        if (isReady())
            AudioService.audioService.play(ayah, 1)
        else Log.e("Error", "Media Controller is not ready")
    }

    override fun playSurah(surah: Int, qari: String) {
        if (isReady())
            QuranAPI.SURAH.getSurahById(surah)?.run {
                AudioService.audioService.play(startId, numberOfAyahs)
            }
        else Log.e("Error", "Media Controller is not ready")
    }

    override fun playJuz(juz: Int, qari: String) {
        if (isReady())
            QuranAPI.JUZ.getJuzById(juz)?.run {
                AudioService.audioService.play(startId, totalAyah)
            }
        else Log.e("Error", "Media Controller is not ready")
    }

    override fun playPage(page: Int, qari: String) {
//        if (isReady())
//            QuranAPI.JUZ.getJuzById(juz)?.run {
//                AudioService.audioService.play(startId, totalAyah)
//            }
//        else Log.e("Error", "Media Controller is not ready")
    }

    override fun pause() {
        if (mediaController.playbackState?.state == PlaybackStateCompat.STATE_PLAYING)
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