package com.nelu.quran_api.data.db

import android.app.Application
import com.nelu.quran_api.binary.BinaryJuz
import com.nelu.quran_api.data.db.dao.DaoJuz
import com.nelu.quran_api.data.model.ModelJuz
import com.nelu.quran_api.data.repository.base.BaseJuz

class ImplJuz(
    application: Application
): BinaryJuz(application), DaoJuz {

    override fun getJuzList(): List<ModelJuz> {
        TODO("Not yet implemented")
    }

    override fun getJuzById(juzId: Int): ModelJuz? {
        TODO("Not yet implemented")
    }

    override fun getJuzForPage(page: Int): ModelJuz {
        TODO("Not yet implemented")
    }

    override fun getJuzForAyah(ayahId: Int): ModelJuz? {
        TODO("Not yet implemented")
    }
}