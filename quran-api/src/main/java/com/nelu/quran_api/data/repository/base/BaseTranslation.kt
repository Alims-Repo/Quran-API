package com.nelu.quran_api.data.repository.base

import com.nelu.quran_api.data.model.ModelTranslator

interface BaseTranslation {

    fun getTranslationList(): List<ModelTranslator>

    fun getLocalTranslationList(): List<ModelTranslator>

    fun downloadTranslation(
        listener: TranslationDownloadListener
    )

    interface TranslationDownloadListener {
        fun onDownloadSuccess()
        fun onDownloadFailure()
        fun onDownloadProgress(progress: Int)
    }
}