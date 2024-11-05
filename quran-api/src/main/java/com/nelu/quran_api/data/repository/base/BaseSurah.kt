package com.nelu.quran_api.data.repository.base

interface BaseSurah {

    fun getSurahList(): List<String>

    fun getSurahById(surahId: Int): String?

    fun getSurahForPage(page: Int): List<String>

    fun getSurahForAyah(ayahId: Int): String?

    fun getSurahByName(surahName: String): List<String>
}