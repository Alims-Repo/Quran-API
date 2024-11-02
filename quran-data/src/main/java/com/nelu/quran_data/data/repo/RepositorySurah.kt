package com.nelu.quran_data.data.repo

import com.nelu.quran_data.data.model.ModelSurah
import com.nelu.quran_data.data.model.ModelSurah.Companion.getSurah
import com.nelu.quran_data.data.repo.base.BaseSurah
import com.nelu.quran_data.utils.parser.SurahInfoParser.readSurahInfo
import org.json.JSONArray

class RepositorySurah : BaseSurah {

    override fun getSurahInfo(): List<ModelSurah> {
        return readSurahInfo() //JSONArray(Surah.json).toSurahList()
    }

    override fun getSurahInfo(number: Int): ModelSurah? {
        return readSurahInfo(number)
    }

    override fun getSurahInfo(query: String): List<ModelSurah> {
        return getSurahInfo().filter {
            it.arabicName.contains(query, ignoreCase = true) ||
                    it.englishName.contains(query, ignoreCase = true) ||
                    it.revelationType.contains(query, ignoreCase = true) ||
                    it.englishTranslation.contains(query, ignoreCase = true)
        }
    }

    override fun getSurahInfoByID(ayahId: Int): ModelSurah? {
        var numberOfAyah = 0
        return getSurahInfo().find { surah ->
            numberOfAyah += surah.numberOfAyahs
            numberOfAyah >= ayahId
        }
    }

    override fun getSurahInfoByPage(ayahId: Int): ModelSurah? {
        TODO("Not yet implemented")
    }

    override fun getSurahInfoByJuz(ayahId: Int): ModelSurah? {
        TODO("Not yet implemented")
    }
}