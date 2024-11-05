package com.nelu.quran_api.data.repository

import com.nelu.quran_api.data.db.dao.DaoSurah
import com.nelu.quran_api.data.model.ModelSurah
import com.nelu.quran_api.data.repository.base.BaseSurah

class RepositorySurah(
    private val daoSurah: DaoSurah
) : BaseSurah {

    override fun getSurahList(): List<ModelSurah> {
        return daoSurah.getSurahList()
    }

    override fun getSurahById(surahId: Int): ModelSurah? {
        return daoSurah.getSurahById(surahId)
    }

    override fun getSurahForPage(page: Int): List<ModelSurah> {
        return daoSurah.getSurahForPage(page)
    }

    override fun getSurahForAyah(ayahId: Int): ModelSurah? {
        return daoSurah.getSurahForAyah(ayahId)
    }

    override fun getSurahByName(surahName: String): List<ModelSurah> {
        return daoSurah.getSurahByName(surahName)
    }
}