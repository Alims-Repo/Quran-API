package com.nelu.quran_api.data.db

import android.app.Application
import com.nelu.quran_api.binary.BinarySurah
import com.nelu.quran_api.data.model.ModelQuran
import com.nelu.quran_api.data.model.ModelSurah

class DatabaseSurah(
    application: Application
) : DaoSurah {

    private val binarySurah = BinarySurah(application)

    override fun getSurahList(): List<ModelSurah> {
        return binarySurah.getSurahList().toList()
    }

    override fun getSurahById(surahId: Int): ModelSurah? {
        return binarySurah.getSurahById(surahId)
    }

    override fun getSurahForPage(page: Int): List<ModelSurah> {
        return binarySurah.getSurahByPage(page)
    }

    override fun getSurahForAyah(ayahId: Int): ModelSurah? {
        return binarySurah.getSurahByAyah(ayahId)
    }

    override fun getSurahByName(surahName: String): List<ModelSurah> {
        return binarySurah.getSurahByName(surahName)
    }

    // Juz Part
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

    override fun getQuranList(translations: List<String>): List<ModelQuran> {
        TODO("Not yet implemented")
    }

    override fun getQuranForSurah(
        surahId: Int,
        translations: List<String>
    ): List<ModelQuran> {
        TODO("Not yet implemented")
    }

    override fun getQuranForJuz(
        juzId: Int,
        translations: List<String>
    ): List<ModelQuran> {
        TODO("Not yet implemented")
    }

    override fun getQuranForPage(
        pageId: Int,
        translations: List<String>
    ): List<ModelQuran> {
        TODO("Not yet implemented")
    }

    override fun getQuranByAyahId(
        ayahId: Int,
        translations: List<String>
    ): ModelQuran? {
        TODO("Not yet implemented")
    }

    override fun getQuranForSurahAndAyah(
        surahId: Int,
        ayahId: Int,
        translations: List<String>
    ): ModelQuran? {
        TODO("Not yet implemented")
    }
}