package com.nelu.quran_data.data.repo.base

import com.nelu.quran_data.data.model.ModelJuz

interface BaseJuz {

    // Basic API for Juz
    fun getJuzInfo(): List<ModelJuz>

    fun getJuzInfo(number: Int): ModelJuz?

    // Complex API for Juz
    fun getJuzByAyahId(ayahId: Int): ModelJuz?

    fun getJuzBySurah(surah: Int): List<ModelJuz>

    fun getJuzBySurahAndAyah(surah: Int, ayah: Int): ModelJuz?
}