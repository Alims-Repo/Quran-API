package com.nelu.quran_api.data.db

import android.app.Application
import android.util.Log
import com.nelu.quran_api.binary.BinaryQuran
import com.nelu.quran_api.data.db.dao.DaoQuran
import com.nelu.quran_api.data.model.ModelQuran
import java.nio.charset.StandardCharsets
import java.text.Normalizer

class ImplQuran(
    application: Application
) : BinaryQuran(application), DaoQuran {

    override fun getQuranList(
        translations: List<String>
    ): List<ModelQuran> {
        return quranList(translations)
    }

    override fun searchQuran(
        query: String,
        translations: List<String>
    ): List<ModelQuran> {
        val lowerCaseQuery = query.lowercase()

        return getQuranList(translations).filter { model ->
            model.arabic.lowercase().contains(lowerCaseQuery)
                    || model.translation.any { translationText ->
                        translationText.lowercase().contains(lowerCaseQuery)
                    }
        }
    }

    override fun getQuranForSurah(
        surahId: Int,
        translations: List<String>
    ): List<ModelQuran> {
        return quranList(translations).filter { it.surah == surahId }
    }

    override fun getQuranForJuz(
        juzId: Int,
        translations: List<String>
    ): List<ModelQuran> {
        return quranList(translations).filter { it.juz == juzId }
    }

    override fun getQuranForPage(
        pageId: Int,
        translations: List<String>
    ): List<ModelQuran> {
        return quranList(translations).filter { it.page == pageId }
    }

    override fun getQuranByAyahId(
        ayahId: Int,
        translations: List<String>
    ): ModelQuran? {
        return quranList(translations).find { it.id == ayahId }
    }

    override fun getQuranForSurahAndAyah(
        surahId: Int,
        ayahId: Int,
        translations: List<String>
    ): ModelQuran? {
        return quranList(translations).find {
            it.surah == surahId && it.ayahInSurah == ayahId
        }
    }

    data class ModelSearchQuran(val id: Int, val arabicBinary: ByteArray, val translationBinary: List<ByteArray>)
}