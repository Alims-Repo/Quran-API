package com.nelu.quran_api.data.db

import android.app.Application
import com.nelu.quran_api.data.db.dao.DaoTranslation
import com.nelu.quran_api.data.model.ModelTranslator

class ImplTranslation(
    application: Application
) : DaoTranslation {

    override fun getTranslationList(): List<ModelTranslator> {
        TODO("Not yet implemented")
    }
}