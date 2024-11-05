package com.nelu.quran_api.data.repository

import com.nelu.quran_api.data.db.DaoQuran
import com.nelu.quran_api.data.repository.base.BasePage

class RepositoryPage(
    private val daoQuran: DaoQuran
) : BasePage {

    override fun getPageList(): List<String> {
        TODO("Not yet implemented")
    }

    override fun getPageByNumber(pageId: Int): String? {
        TODO("Not yet implemented")
    }

    override fun getPageForAyah(ayahId: Int): String? {
        TODO("Not yet implemented")
    }
}