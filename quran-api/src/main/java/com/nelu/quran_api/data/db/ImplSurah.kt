package com.nelu.quran_api.data.db

import android.app.Application
import com.nelu.quran_api.binary.BinarySurah
import com.nelu.quran_api.data.db.dao.DaoSurah
import com.nelu.quran_api.data.model.ModelQuran
import com.nelu.quran_api.data.model.ModelSurah

class ImplSurah(
    application: Application
) : BinarySurah(application), DaoSurah {

    override fun getSurahList(): List<ModelSurah> {
        return surahList().toList()
    }

    override fun getSurahById(surahId: Int): ModelSurah? {
        return surahById(surahId)
    }

    override fun getSurahForPage(page: Int): List<ModelSurah> {
        return surahByPage(page)
    }

    override fun getSurahForAyah(ayahId: Int): ModelSurah? {
        return surahByAyah(ayahId)
    }

    override fun getSurahByName(surahName: String): List<ModelSurah> {
        return surahByName(surahName)
    }

}