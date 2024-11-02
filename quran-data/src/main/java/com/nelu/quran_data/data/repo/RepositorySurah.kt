package com.nelu.quran_data.data.repo

import com.nelu.quran_data.data.model.ModelSurah
import com.nelu.quran_data.data.repo.base.BaseJuz
import com.nelu.quran_data.data.repo.base.BasePage
import com.nelu.quran_data.data.repo.base.BaseSurah
import com.nelu.quran_data.di.QuranData
import com.nelu.quran_data.utils.parser.SurahInfoParser.readSurahInfo

class RepositorySurah: BaseSurah {

    override fun getSurahInfo(): List<ModelSurah> {
        return readSurahInfo()
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

    override fun getSurahInfoByAyah(ayahId: Int): ModelSurah? {
        var numberOfAyah = 0
        return getSurahInfo().find { surah ->
            numberOfAyah += surah.numberOfAyahs
            numberOfAyah >= ayahId
        }
    }

    override fun getSurahInfoByPage(pageNo: Int): List<ModelSurah> {
        return QuranData.page.getPageInfo(pageNo)?.let { pageInfo ->
            (pageInfo.start..pageInfo.end)
                .mapNotNull { getSurahInfoByAyah(it) }
                .distinctBy { it.number }
        } ?: emptyList()
    }

    override fun getSurahInfoByJuz(juzNo: Int): List<ModelSurah> {
        return QuranData.juz.getJuzInfo(juzNo)?.let { juzInfo->
            (juzInfo.startId..(juzInfo.startId + juzInfo.totalAyah))
                .mapNotNull { getSurahInfoByAyah(it) }
                .distinctBy { it.number }
        } ?: emptyList()
    }
}