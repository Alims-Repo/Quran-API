package com.nelu.quran_api.data.db

import android.app.Application
import android.content.Context
import com.nelu.quran_api.binary.BinaryQari
import com.nelu.quran_api.data.db.dao.DaoQari
import com.nelu.quran_api.data.model.ModelQari

class ImplQari(
    override val context: Context
) : BinaryQari(context), DaoQari {

    override fun getQaris(): List<ModelQari> {
        return qarisList()
    }
}