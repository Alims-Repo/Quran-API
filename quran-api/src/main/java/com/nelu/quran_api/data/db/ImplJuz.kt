package com.nelu.quran_api.data.db

import android.app.Application
import com.nelu.quran_api.binary.BinaryJuz
import com.nelu.quran_api.data.repository.base.BaseJuz

class ImplJuz(
    application: Application
): BinaryJuz(application), BaseJuz {

    override fun getJuzList(): List<String> {
        TODO("Not yet implemented")
    }

    override fun getJuzById(juzId: Int): String? {
        TODO("Not yet implemented")
    }

    override fun getJuzForPage(page: Int): String {
        TODO("Not yet implemented")
    }

    override fun getJuzForAyah(ayahId: Int): String? {
        TODO("Not yet implemented")
    }
}