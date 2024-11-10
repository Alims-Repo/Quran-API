package com.nelu.quran_api.data.repository.base

import android.support.v4.media.session.PlaybackStateCompat
import androidx.lifecycle.LiveData

interface BaseAudio {

    fun playAyah(ayah: Int, qari: String = "")

    fun playSurah(surah: Int, qari: String = "")

    fun playJuz(juz: Int, qari: String = "")

    fun playPage(page: Int, qari: String = "")

    fun pause()

    fun resume() : Boolean

    fun stop()

    fun getQaris() : List<String>

    fun getPlaybackState() : LiveData<PlaybackStateCompat?>
}