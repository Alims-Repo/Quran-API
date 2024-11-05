package com.nelu.quran_api.data.repository

import com.nelu.quran_api.data.db.DaoSurah
import com.nelu.quran_api.data.repository.base.BaseTranslation

class RepositoryTranslation(
    private val daoSurah: DaoSurah
) : BaseTranslation {

    override fun getTranslationList(): List<String> {
        TODO("Not yet implemented")
    }
}