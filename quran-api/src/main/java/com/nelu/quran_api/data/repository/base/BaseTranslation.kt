package com.nelu.quran_api.data.repository.base

import com.nelu.quran_api.data.model.ModelTranslator

interface BaseTranslation {

    fun getTranslationList(): List<ModelTranslator>

    fun getLocalTranslationList(): List<ModelTranslator>

    fun downloadTranslation(
        code: String,
        listener: TranslationDownloadListener
    )

    interface TranslationDownloadListener {
        fun onSuccess()
        fun onFailure(e: Exception)
        fun onProgress(type: Type, progress: Int)

        enum class Type {
            DOWNLOAD, ENCODE
        }
    }
}