package com.nelu.quran_api.data.db.dao

import com.nelu.quran_api.data.model.ModelTranslator
import com.nelu.quran_api.data.repository.base.BaseTranslation.TranslationDownloadListener

interface DaoTranslation {

    fun getTranslationList(): List<ModelTranslator>

    fun getLocalTranslationList(): List<ModelTranslator>
}