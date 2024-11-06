package com.nelu.quran_api.data.repository

import com.nelu.quran_api.data.db.dao.DaoSurah
import com.nelu.quran_api.data.db.dao.DaoTranslation
import com.nelu.quran_api.data.model.ModelTranslator
import com.nelu.quran_api.data.repository.base.BaseTranslation

class RepositoryTranslation(
    private val daoTranslation: DaoTranslation
) : BaseTranslation {

    override fun getTranslationList(): List<ModelTranslator> {
        return daoTranslation.getTranslationList()
    }

    override fun getLocalTranslationList(): List<ModelTranslator> {
        return daoTranslation.getLocalTranslationList()
    }

    override fun downloadTranslation(listener: BaseTranslation.TranslationDownloadListener) {
        TODO("Not yet implemented")
    }
}