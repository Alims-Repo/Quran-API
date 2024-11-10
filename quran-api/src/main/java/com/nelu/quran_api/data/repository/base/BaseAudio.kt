package com.nelu.quran_api.data.repository.base

import android.support.v4.media.session.PlaybackStateCompat
import androidx.lifecycle.LiveData

interface BaseAudio {

    fun playAyah(ayah: Int)

    fun playSurah(surah: Int)

    fun playJuz(juz: Int)

    fun playPage(page: Int)

    fun pause()

    fun resume() : Boolean

    fun stop()

    fun getPlaybackState() : LiveData<PlaybackStateCompat?>
}