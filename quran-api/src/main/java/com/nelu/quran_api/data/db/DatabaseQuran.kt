package com.nelu.quran_api.data.db

import android.app.Application

class DatabaseQuran(
    private val application: Application
) : DaoQuran {

    override fun getSurahList(): List<String> {
        TODO("Not yet implemented")
    }

    override fun getSurahById(surahId: Int): String? {
        TODO("Not yet implemented")
    }

    override fun getSurahForPage(page: Int): List<String> {
        TODO("Not yet implemented")
    }

    override fun getSurahForAyah(ayahId: Int): String? {
        TODO("Not yet implemented")
    }

    override fun getSurahByName(surahName: String): List<String> {
        TODO("Not yet implemented")
    }

    override fun getJuzList(): List<String> {
        TODO("Not yet implemented")
    }

    override fun getJuzById(juzId: Int): String? {
        TODO("Not yet implemented")
    }

    override fun getJuzForPage(page: Int): String {
        TODO("Not yet implemented")
    }

    override fun getJuzForAyah(ayahId: Int): String? {
        TODO("Not yet implemented")
    }

    override fun getPageList(): List<String> {
        TODO("Not yet implemented")
    }

    override fun getPageByNumber(pageId: Int): String? {
        TODO("Not yet implemented")
    }

    override fun getPageForAyah(ayahId: Int): String? {
        TODO("Not yet implemented")
    }

    override fun getTranslationList(): List<String> {
        TODO("Not yet implemented")
    }

    override fun getQuranList(translations: List<String>): List<String> {
        TODO("Not yet implemented")
    }

    override fun getQuranForSurah(
        surahId: Int,
        translations: List<String>
    ): List<String> {
        TODO("Not yet implemented")
    }

    override fun getQuranForJuz(
        juzId: Int,
        translations: List<String>
    ): List<String> {
        TODO("Not yet implemented")
    }

    override fun getQuranForPage(
        pageId: Int,
        translations: List<String>
    ): List<String> {
        TODO("Not yet implemented")
    }

    override fun getQuranByAyahId(
        ayahId: Int,
        translations: List<String>
    ): String? {
        TODO("Not yet implemented")
    }

    override fun getQuranForSurahAndAyah(
        surahId: Int,
        ayahId: Int,
        translations: List<String>
    ): String? {
        TODO("Not yet implemented")
    }
}