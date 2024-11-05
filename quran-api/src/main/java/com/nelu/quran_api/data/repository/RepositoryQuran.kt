package com.nelu.quran_api.data.repository

import com.nelu.quran_api.data.db.DaoSurah
import com.nelu.quran_api.data.repository.base.BaseQuran

class RepositoryQuran(
    private val daoSurah: DaoSurah
) : BaseQuran {

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