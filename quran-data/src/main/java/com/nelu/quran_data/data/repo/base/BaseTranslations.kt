package com.nelu.quran_data.data.repo.base

import com.nelu.quran_data.data.model.ModelTranslationInfo

interface BaseTranslations {

    fun getTranslationList(): List<ModelTranslationInfo>

    fun getTranslationInfo(id: String): ModelTranslationInfo

    fun searchTranslation(query: String): List<ModelTranslationInfo>

    fun downloadTranslation(id: String)

    fun deleteTranslation(id: String)

}