package com.nelu.quran_api.data.repository.base

interface BaseAudio {

    fun playAyah(ayah: Int)

    fun playSurah(surah: Int)

    fun playJuz(juz: Int)

    fun playPage(page: Int)

    fun pause()

    fun resume() : Boolean

    fun stop()
}