package com.nelu.quran_api.data.db.dao

import com.nelu.quran_api.data.model.ModelJuz

/**
 * # DaoJuz
 *
 * Interface for accessing Juz-related data in the Quran. This DAO provides methods
 * to retrieve the list of Juz, get a specific Juz by its ID, and find a Juz based
 * on page number or Ayah ID. Each method is designed to provide structured access
 * to Juz information.
 */
interface DaoJuz {

    /**
     * Retrieves a list of all Juz in the Quran.
     *
     * @return A list of [ModelJuz] objects representing each Juz in the Quran.
     */
    fun getJuzList(): List<ModelJuz>

    /**
     * Retrieves a specific Juz by its ID.
     *
     * @param juzId The unique identifier (1 to 30) of the Juz to retrieve.
     * @return A [ModelJuz] object for the specified Juz, or `null` if not found.
     */
    fun getJuzById(juzId: Int): ModelJuz?

    /**
     * Retrieves the Juz that contains the specified page number.
     *
     * @param page The page number in the Quran.
     * @return A [ModelJuz] object representing the Juz for the given page, or `null` if no Juz is found.
     */
    fun getJuzForPage(page: Int): ModelJuz?

    /**
     * Retrieves the Juz that contains the specified Ayah (verse) ID.
     *
     * @param ayahId The unique ID of the Ayah.
     * @return A [ModelJuz] object representing the Juz for the given Ayah, or `null` if no Juz is found.
     */
    fun getJuzForAyah(ayahId: Int): ModelJuz?
}