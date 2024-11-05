package com.nelu.quran_api.data.db.dao

import com.nelu.quran_api.data.model.ModelTranslator

interface DaoTranslation {

    fun getTranslationList(): List<ModelTranslator>
}