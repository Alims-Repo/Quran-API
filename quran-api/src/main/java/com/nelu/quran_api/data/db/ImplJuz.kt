package com.nelu.quran_api.data.db

import android.app.Application
import android.util.Log
import com.nelu.quran_api.binary.BinaryJuz
import com.nelu.quran_api.data.db.dao.DaoJuz
import com.nelu.quran_api.data.model.ModelJuz
import com.nelu.quran_api.data.repository.base.BaseJuz

class ImplJuz(
    application: Application
): BinaryJuz(application), DaoJuz {

    override fun getJuzList(): List<ModelJuz> {
        return juzList()
    }

    override fun getJuzById(juzId: Int): ModelJuz? {
        return juzList().find { it.number == juzId }
    }

    override fun getJuzForPage(page: Int): ModelJuz? {
        return juzByPage(page)
    }

    override fun getJuzForAyah(ayahId: Int): ModelJuz? {
        Log.e("JUZ", juzForAyah(ayahId).toString())
        Log.e("JUZ All", juzList().toString())
        return juzList().find { it.number == juzForAyah(ayahId) }
    }
}