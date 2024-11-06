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
        return daoQuran.getQuranForSurah(surahId, translations)
    }

    override fun getQuranForJuz(
        juzId: Int,
        translations: List<String>
    ): List<ModelQuran> {
        return daoQuran.getQuranForJuz(juzId, translations)
    }

    override fun getQuranForPage(
        pageId: Int,
        translations: List<String>
    ): List<ModelQuran> {
        return daoQuran.getQuranForPage(pageId, translations)
    }

    override fun getQuranByAyahId(
        ayahId: Int,
        translations: List<String>
    ): ModelQuran? {
        return daoQuran.getQuranByAyahId(ayahId, translations)
    }

    override fun getQuranForSurahAndAyah(
        surahId: Int,
        ayahId: Int,
        translations: List<String>
    ): ModelQuran? {
        return daoQuran.getQuranForSurahAndAyah(surahId, ayahId, translations)
    }
}
