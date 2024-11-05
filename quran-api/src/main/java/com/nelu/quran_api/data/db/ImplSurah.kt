package com.nelu.quran_api.data.db

import android.app.Application
import com.nelu.quran_api.binary.BinarySurah
import com.nelu.quran_api.data.db.dao.DaoSurah
import com.nelu.quran_api.data.model.ModelQuran
import com.nelu.quran_api.data.model.ModelSurah

class ImplSurah(
    application: Application
) : BinarySurah(application), DaoSurah {

    override fun getSurahList(): List<ModelSurah> {
        return surahList().toList()
    }

    override fun getSurahById(surahId: Int): ModelSurah? {
        return surahById(surahId)
    }

    override fun getSurahForPage(page: Int): List<ModelSurah> {
        return surahByPage(page)
    }

    override fun getSurahForAyah(ayahId: Int): ModelSurah? {
        return surahByAyah(ayahId)
    }

    override fun getSurahByName(surahName: String): List<ModelSurah> {
        return surahByName(surahName)
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