package com.nelu.quran_api.data.db.dao

import com.nelu.quran_api.data.model.ModelQuran

/**
 * # DaoQuran
 *
 * Interface for accessing Quranic data with various filters and search options. This DAO provides
 * methods to retrieve Quranic verses (Ayahs) based on multiple criteria, such as Surah, Juz, page,
 * and Ayah ID, along with search functionality. It also supports loading translations to include
 * with the Arabic text.
 */
interface DaoQuran {

    /**
     * Retrieves the entire list of Quranic Ayahs, optionally including translations.
     *
     * @param translations A list of translation codes to load with the Arabic text.
     * @return A list of [ModelQuran] objects representing the Quran with optional translations.
     */
    fun getQuranList(translations: List<String>): List<ModelQuran>

    /**
     * Searches for Ayahs that match the specified query string.
     *
     * @param query The text query to search within Ayahs.
     * @param translations A list of translation codes to load with the Arabic text.
     * @return A list of [ModelQuran] objects that match the search query, with optional translations.
     */
    fun searchQuran(query: String, translations: List<String>): List<ModelQuran>

    /**
     * Retrieves all Ayahs in a specific Surah.
     *
     * @param surahId The ID of the Surah.
     * @param translations A list of translation codes to load with the Arabic text.
     * @return A list of [ModelQuran] objects for the specified Surah, with optional translations.
     */
    fun getQuranForSurah(surahId: Int, translations: List<String>): List<ModelQuran>

    /**
     * Retrieves all Ayahs in a specific Juz.
     *
     * @param juzId The ID of the Juz.
     * @param translations A list of translation codes to load with the Arabic text.
     * @return A list of [ModelQuran] objects for the specified Juz, with optional translations.
     */
    fun getQuranForJuz(juzId: Int, translations: List<String>): List<ModelQuran>

    /**
     * Retrieves all Ayahs on a specific page.
     *
     * @param pageId The page number in the Quran.
     * @param translations A list of translation codes to load with the Arabic text.
     * @return A list of [ModelQuran] objects for the specified page, with optional translations.
     */
    fun getQuranForPage(pageId: Int, translations: List<String>): List<ModelQuran>

    /**
     * Retrieves a specific Ayah by its unique Ayah ID.
     *
     * @param ayahId The unique ID of the Ayah.
     * @param translations A list of translation codes to load with the Arabic text.
     * @return A [ModelQuran] object for the specified Ayah, or `null` if not found.
     */
    fun getQuranByAyahId(ayahId: Int, translations: List<String>): ModelQuran?

    /**
     * Retrieves a specific Ayah within a specific Surah.
     *
     * @param surahId The ID of the Surah.
     * @param ayahId The number of the Ayah within the Surah.
     * @param translations A list of translation codes to load with the Arabic text.
     * @return A [ModelQuran] object for the specified Surah and Ayah, or `null` if not found.
     */
    fun getQuranForSurahAndAyah(surahId: Int, ayahId: Int, translations: List<String>): ModelQuran?

    fun getQuranByPage(translations: List<String>) : List<Pair<Int, List<ModelQuran>>>
}