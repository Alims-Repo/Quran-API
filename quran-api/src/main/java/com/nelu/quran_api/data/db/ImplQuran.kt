package com.nelu.quran_api.data.db

import android.app.Application
import com.nelu.quran_api.binary.BinaryQuran
import com.nelu.quran_api.data.db.dao.DaoQuran
import com.nelu.quran_api.data.model.ModelQuran

class ImplQuran(
    application: Application
) : BinaryQuran(application), DaoQuran {

    override fun getQuranList(translations: List<String>): List<ModelQuran> {
        return quranList(translations)
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