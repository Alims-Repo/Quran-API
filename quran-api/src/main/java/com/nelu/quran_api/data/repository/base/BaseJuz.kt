package com.nelu.quran_api.data.repository.base

import com.nelu.quran_api.data.model.ModelJuz

/**
 * # BaseJuz
 *
 * Interface for managing access to Juz-related data in the Quran. This interface provides methods
 * to retrieve a list of all Juz, find a specific Juz by its ID, and locate a Juz based on page number
 * or Ayah ID. Each method enables structured access to Quranic Juz information.
 */
interface BaseJuz {

    /**
     * Retrieves a list of all Juz in the Quran.
     *
     * @return A list of [ModelJuz] objects representing each Juz in the Quran.
     */
    fun getJuzList(): List<ModelJuz>

    /**
     * Finds a specific Juz by its unique Juz ID.
     *
     * @param juzId The unique identifier (1 to 30) of the Juz to retrieve.
     * @return A [ModelJuz] object for the specified Juz, or `null` if not found.
     */
    fun getJuzById(juzId: Int): ModelJuz?

    /**
     * Retrieves the Juz that corresponds to a specific page number in the Quran.
     *
     * @param page The page number in the Quran.
     * @return A [ModelJuz] object representing the Juz for the given page, or `null` if no Juz is found.
     */
    fun getJuzForPage(page: Int): ModelJuz?

    /**
     * Finds the Juz that contains the specified Ayah (verse) ID.
     *
     * @param ayahId The unique ID of the Ayah.
     * @return A [ModelJuz] object representing the Juz for the given Ayah, or `null` if no Juz is found.
     */
    fun getJuzForAyah(ayahId: Int): ModelJuz?
}