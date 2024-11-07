package com.nelu.quran_api.data.repository.base

import com.nelu.quran_api.data.model.ModelQuran

interface BaseQuran {

    fun getQuranList(translations: List<String>): List<ModelQuran>

    fun searchQuran(query: String, translations: List<String>): List<ModelQuran>

    fun getQuranForSurah(surahId: Int, translations: List<String>): List<ModelQuran>

    fun getQuranForJuz(juzId: Int, translations: List<String>): List<ModelQuran>

    fun getQuranForPage(pageId: Int, translations: List<String>): List<ModelQuran>

    fun getQuranByAyahId(ayahId: Int, translations: List<String>): ModelQuran?

    fun getQuranForSurahAndAyah(surahId: Int, ayahId: Int, translations: List<String>): ModelQuran?
}