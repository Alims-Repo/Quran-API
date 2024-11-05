package com.nelu.quran_api.data.repository

import com.nelu.quran_api.data.db.dao.DaoJuz
import com.nelu.quran_api.data.db.dao.DaoSurah
import com.nelu.quran_api.data.model.ModelJuz
import com.nelu.quran_api.data.repository.base.BaseJuz

class RepositoryJuz(
    private val daoJuz: DaoJuz
) : BaseJuz {

    override fun getJuzList(): List<ModelJuz> {
        return daoJuz.getJuzList()
    }

    override fun getJuzById(juzId: Int): ModelJuz? {
        return daoJuz.getJuzById(juzId)
    }

    override fun getJuzForPage(page: Int): ModelJuz? {
        return daoJuz.getJuzForPage(page)
    }

    override fun getJuzForAyah(ayahId: Int): ModelJuz? {
        return daoJuz.getJuzForAyah(ayahId)
    }
}