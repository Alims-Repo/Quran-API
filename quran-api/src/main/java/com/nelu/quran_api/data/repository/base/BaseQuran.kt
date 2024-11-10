package com.nelu.quran_api.data.repository.base

import com.nelu.quran_api.data.model.ModelQuran

/**
 * # BaseQuran
 *
 * Interface for managing access to Quranic data. This interface provides methods to retrieve lists of Ayahs (verses)
 * with optional translations, as well as to search and filter Ayahs based on Surah, Juz, page, Ayah ID, and specific
 * Surah-Ayah combinations. Each method facilitates structured and efficient access to Quranic content.
 */
interface BaseQuran {

    /**
     * Retrieves the entire list of Quranic Ayahs, optionally including translations.
     *
     * @param translations A list of translation codes to load with the Arabic text.
     * @return A list of [ModelQuran] objects representing the Quran with optional translations.
     */
    fun getQuranList(translations: List<String>): List<ModelQuran>

    /**
     * Searches for Ayahs that match the specified query string in Arabic or translations.
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