package com.nelu.quran_data.data.repo

import com.nelu.quran_data.data.model.ModelTranslationInfo
import com.nelu.quran_data.data.repo.base.BaseTranslations

class RepositoryTranslations : BaseTranslations {

    override fun getTranslationList(): List<ModelTranslationInfo> {
        TODO("Not yet implemented")
    }

    override fun getTranslationInfo(id: String): ModelTranslationInfo {
        TODO("Not yet implemented")
    }

    override fun searchTranslation(query: String): List<ModelTranslationInfo> {
        TODO("Not yet implemented")
    }

    override fun downloadTranslation(id: String) {
        TODO("Not yet implemented")
    }

    override fun deleteTranslation(id: String) {
        TODO("Not yet implemented")
    }
}