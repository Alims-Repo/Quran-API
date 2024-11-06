package com.nelu.quran_api.data.db

import android.app.Application
import android.util.Log
import com.nelu.quran_api.binary.BinaryTranslation
import com.nelu.quran_api.data.db.dao.DaoTranslation
import com.nelu.quran_api.data.model.ModelTranslator

class ImplTranslation(
    private val application: Application
) : BinaryTranslation(application), DaoTranslation {

    override fun getTranslationList(): List<ModelTranslator> {
        return translationList()
    }

    override fun getLocalTranslationList(): List<ModelTranslator> {
        return (application.filesDir.listFiles()?.map { it.name } ?: emptyList()).run {
            translationList().filter {
                contains(it.code + ".dat")
            }
        }
    }
}