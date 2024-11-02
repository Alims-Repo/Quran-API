package com.nelu.quran_data.data.repo

import com.nelu.quran_data.data.model.ModelJuz
import com.nelu.quran_data.data.model.ModelJuz.Companion.toJuz
import com.nelu.quran_data.data.model.ModelJuz.Companion.toJuzList
import com.nelu.quran_data.data.repo.base.BaseJuz
import com.nelu.quran_data.data.repo.base.BaseSurah
import com.nelu.quran_data.utils.parser.JuzInfoParser.readJuzInfo
import org.json.JSONArray

class RepositoryJuz(
    private val baseSurah: BaseSurah
) : BaseJuz {

    override fun getJuzInfo(): List<ModelJuz> {
        return readJuzInfo() //JSONArray(Juz.json).toJuzList()
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
        baseSurah.getSurahInfo(surah)?.numberOfAyahs
        return getJuzInfo().filter { juz ->
            juz.startSurah <= surah && surah < (juz.startId + juz.totalAyah)
        }
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