package com.nelu.quran_api.data.repository.base

import com.nelu.quran_api.data.model.ModelSurah

/**
 * # BaseSurah
 *
 * Interface for managing access to Surah-related data in the Quran. This interface provides methods
 * to retrieve a list of Surahs, find a specific Surah by its ID, and locate Surahs based on page number,
 * Ayah (verse) ID, or Surah name. Each method is designed to enable structured access to Surah information.
 */
interface BaseSurah {

    /**
     * Retrieves a list of all Surahs in the Quran.
     *
     * @return A list of [ModelSurah] objects representing each Surah in the Quran.
     */
    fun getSurahList(): List<ModelSurah>

    /**
     * Finds a specific Surah by its unique Surah ID.
     *
     * @param surahId The unique identifier (1 to 114) of the Surah to retrieve.
     * @return A [ModelSurah] object for the specified Surah, or `null` if not found.
     */
    fun getSurahById(surahId: Int): ModelSurah?

    /**
     * Retrieves all Surahs that start on a specific page.
     *
     * @param page The page number in the Quran.
     * @return A list of [ModelSurah] objects representing the Surahs that start on the specified page.
     */
    fun getSurahForPage(page: Int): List<ModelSurah>

    /**
     * Finds the Surah that contains the specified Ayah (verse) ID.
     *
     * @param ayahId The unique ID of the Ayah.
     * @return A [ModelSurah] object representing the Surah containing the given Ayah, or `null` if not found.
     */
    fun getSurahForAyah(ayahId: Int): ModelSurah?

    /**
     * Retrieves all Surahs that match a specific name.
     *
     * @param surahName The name of the Surah to retrieve, either in Arabic or transliterated.
     * @return A list of [ModelSurah] objects that match the given Surah name.
     */
    fun getSurahByName(surahName: String): List<ModelSurah>
}