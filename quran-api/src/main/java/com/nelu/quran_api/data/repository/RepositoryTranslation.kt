package com.nelu.quran_api.data.repository

import com.nelu.quran_api.data.db.DaoQuran
import com.nelu.quran_api.data.repository.base.BaseTranslation

class RepositoryTranslation(
    private val daoQuran: DaoQuran
) : BaseTranslation {

    override fun getTranslationList(): List<String> {
        TODO("Not yet implemented")
    }
}