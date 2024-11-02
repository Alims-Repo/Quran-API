package com.nelu.quran_data.data.repo.base

import com.nelu.quran_data.data.model.ModelSurah

interface BaseSurah {

    // Basic API for Surah
    fun getSurahInfo(): List<ModelSurah>

    fun getSurahInfo(surahNo: Int): ModelSurah?

    fun getSurahInfo(query: String): List<ModelSurah>

    // Complex API for Surah
    fun getSurahInfoByID(ayahId: Int): ModelSurah?

    fun getSurahInfoByPage(ayahId: Int): ModelSurah?

    fun getSurahInfoByJuz(ayahId: Int): ModelSurah?
}