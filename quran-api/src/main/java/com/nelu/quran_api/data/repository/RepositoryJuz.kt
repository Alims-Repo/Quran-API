package com.nelu.quran_api.data.repository

import com.nelu.quran_api.data.db.dao.DaoSurah
import com.nelu.quran_api.data.repository.base.BaseJuz

class RepositoryJuz(
    private val daoSurah: DaoSurah
) : BaseJuz {

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
}