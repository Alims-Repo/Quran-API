package com.nelu.quran_api.data.repository.base

import com.nelu.quran_api.data.model.ModelSurah

interface BaseSurah {

    fun getSurahList(): List<ModelSurah>

    fun getSurahById(surahId: Int): ModelSurah?

    fun getSurahForPage(page: Int): List<ModelSurah>

    fun getSurahForAyah(ayahId: Int): ModelSurah?

    fun getSurahByName(surahName: String): List<ModelSurah>
}