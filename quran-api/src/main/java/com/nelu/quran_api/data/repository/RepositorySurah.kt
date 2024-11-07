package com.nelu.quran_api.data.repository

import com.nelu.quran_api.data.db.dao.DaoSurah
import com.nelu.quran_api.data.model.ModelSurah
import com.nelu.quran_api.data.repository.base.BaseSurah

/**
 * # RepositorySurah
 *
 * This class implements the [BaseSurah] interface by delegating Surah-related data operations
 * to the [DaoSurah] data access object. It provides methods for retrieving lists of Surahs and
 * locating specific Surahs based on various criteria, including Surah ID, page number, Ayah ID,
 * and Surah name.
 *
 * @param daoSurah The Data Access Object (DAO) that handles Surah-related data operations.
 */
class RepositorySurah(
    private val daoSurah: DaoSurah
) : BaseSurah {

    /**
     * Retrieves a list of all Surahs in the Quran.
     *
     * @return A list of [ModelSurah] objects representing each Surah in the Quran.
     */
    override fun getSurahList(): List<ModelSurah> {
        return daoSurah.getSurahList()
    }

    /**
     * Finds a specific Surah by its unique Surah ID.
     *
     * @param surahId The unique identifier (1 to 114) of the Surah to retrieve.
     * @return A [ModelSurah] object for the specified Surah, or `null` if not found.
     */
    override fun getSurahById(surahId: Int): ModelSurah? {
        return daoSurah.getSurahById(surahId)
    }

    /**
     * Retrieves all Surahs that start on a specific page.
     *
     * @param page The page number in the Quran.
     * @return A list of [ModelSurah] objects representing the Surahs that start on the specified page.
     */
    override fun getSurahForPage(page: Int): List<ModelSurah> {
        return daoSurah.getSurahForPage(page)
    }

    /**
     * Finds the Surah that contains the specified Ayah (verse) ID.
     *
     * @param ayahId The unique ID of the Ayah.
     * @return A [ModelSurah] object representing the Surah containing the given Ayah, or `null` if not found.
     */
    override fun getSurahForAyah(ayahId: Int): ModelSurah? {
        return daoSurah.getSurahForAyah(ayahId)
    }

    /**
     * Retrieves all Surahs that match a specific name.
     *
     * @param surahName The name of the Surah to retrieve, either in Arabic or transliterated.
     * @return A list of [ModelSurah] objects that match the given Surah name.
     */
    override fun getSurahByName(surahName: String): List<ModelSurah> {
        return daoSurah.getSurahByName(surahName)
    }
}