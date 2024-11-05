package com.nelu.quran_api.data.repository.base

interface BaseQuran {

    fun getQuranList(translations: List<String>): List<String>

    fun getQuranForSurah(surahId: Int, translations: List<String>): List<String>

    fun getQuranForJuz(juzId: Int, translations: List<String>): List<String>

    fun getQuranForPage(pageId: Int, translations: List<String>): List<String>

    fun getQuranByAyahId(ayahId: Int, translations: List<String>): String?

    fun getQuranForSurahAndAyah(surahId: Int, ayahId: Int, translations: List<String>): String?
}