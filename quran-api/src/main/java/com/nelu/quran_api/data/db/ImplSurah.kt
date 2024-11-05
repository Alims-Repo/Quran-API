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
        return getIndex()
            .filter { it.page == page }
            .distinctBy { it.surah }
            .map { it.surah }
            .let { surah->
                if (surah.size == 1)
                    listOf(surahById(surah.first())!!)
                else surahList().filter {
                    surah.contains(it.number)
            }
        }
    }

    override fun getSurahForAyah(ayahId: Int): ModelSurah? {
        return if (ayahId > 6236 || ayahId < 1) null
        else surahById(getIndex().find { it.id == ayahId }!!.surah)
    }

    override fun getSurahByName(surahName: String): List<ModelSurah> {
        return surahList().filter {
            it.englishName.contains(surahName, true)
                    || it.arabicName.contains(surahName, true)
                    || it.revelationType.contains(surahName, true)
                    || it.englishTranslation.contains(surahName, true)
        }
    }

}