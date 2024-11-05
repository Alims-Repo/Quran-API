package com.nelu.quran_api.data.repository

import com.nelu.quran_api.data.db.DaoQuran
import com.nelu.quran_api.data.repository.base.BaseSurah

class RepositorySurah(
    private val daoQuran: DaoQuran
) : BaseSurah {

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
}