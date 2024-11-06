package com.nelu.quran_api.data.repository

import com.nelu.quran_api.data.db.dao.DaoQuran
import com.nelu.quran_api.data.db.dao.DaoSurah
import com.nelu.quran_api.data.model.ModelQuran
import com.nelu.quran_api.data.repository.base.BaseQuran

class RepositoryQuran(
    private val daoQuran: DaoQuran
) : BaseQuran {

    override fun getQuranList(translations: List<String>): List<ModelQuran> {
        return daoQuran.getQuranList(translations)
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