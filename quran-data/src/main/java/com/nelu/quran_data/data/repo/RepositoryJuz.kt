package com.nelu.quran_data.data.repo

import android.util.Log
import com.nelu.quran_data.data.model.ModelJuz
import com.nelu.quran_data.data.model.ModelJuz.Companion.toJuz
import com.nelu.quran_data.data.model.ModelJuz.Companion.toJuzList
import com.nelu.quran_data.data.repo.base.BaseJuz
import com.nelu.quran_data.data.repo.base.BaseSurah
import com.nelu.quran_data.di.QuranData
import com.nelu.quran_data.utils.parser.JuzInfoParser.readJuzInfo
import org.json.JSONArray

class RepositoryJuz : BaseJuz {

    override fun getJuzInfo(): List<ModelJuz> {
        return readJuzInfo()
    }

    override fun getJuzInfo(number: Int): ModelJuz? {
        return readJuzInfo().find { it.number == number }
    }

    override fun getJuzByAyahId(ayahId: Int): ModelJuz? {
        var cumulativeAyahCount = 0
        return getJuzInfo().find { juz ->
            cumulativeAyahCount += juz.totalAyah
            cumulativeAyahCount >= ayahId
        }
    }

    override fun getJuzBySurah(surah: Int): List<ModelJuz> {
        return QuranData.surah.getSurahInfo(surah)?.run {
            getJuzInfo().filter { juz ->
                (juz.startId + juz.totalAyah) > startId && juz.startId < (startId + numberOfAyahs)
            }
        } ?: emptyList()
    }

    override fun getJuzBySurahAndAyah(surah: Int, ayah: Int): ModelJuz? {
        var cumulativeAyahCount = 0
        return getJuzInfo().find { juz ->
            if (juz.startSurah == surah && ayah <= juz.totalAyah) {
                true
            } else {
                cumulativeAyahCount += juz.totalAyah
                cumulativeAyahCount >= ayah
            }
        }
    }
}