package com.nelu.quran_api.data.db.dao

import android.app.Application
import android.content.Context
import com.nelu.quran_api.data.model.ModelQari

interface DaoQari {

    val context: Context

    fun getQaris() : List<ModelQari>
}