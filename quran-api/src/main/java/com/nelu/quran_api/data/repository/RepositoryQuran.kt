package com.nelu.quran_api.data.repository

import com.nelu.quran_api.data.db.dao.DaoQuran
import com.nelu.quran_api.data.model.ModelQuran
import com.nelu.quran_api.data.repository.base.BaseQuran

/**
 * # RepositoryQuran
 *
 * This class implements the [BaseQuran] interface by delegating Quranic data operations
 * to the [DaoQuran] data access object. It provides methods for retrieving and searching
 * Quranic verses (Ayahs) with optional translations, as well as filtering Ayahs by Surah, Juz,
 * page, and specific Ayah IDs.
 *
 * @param daoQuran The Data Access Object (DAO) that handles Quran-related data operations.
 */
class RepositoryQuran(
    private val daoQuran: DaoQuran
) : BaseQuran {

    /**
     * Retrieves the entire list of Quranic Ayahs, optionally including translations.
     *
     * @param translations A list of translation codes to load with the Arabic text.
     * @return A list of [ModelQuran] objects representing the Quran with optional translations.
     */
    override fun getQuranList(translations: List<String>): List<ModelQuran> {
        return daoQuran.getQuranList(translations)
    }

    /**
     * Searches for Ayahs that match the specified query string in Arabic or translations.
     *
     * @param query The text query to search within Ayahs.
     * @param translations A list of translation codes to load with the Arabic text.
     * @return A list of [ModelQuran] objects that match the search query, with optional translations.
     */
    override fun searchQuran(
        query: String,
        translations: List<String>
    ): List<ModelQuran> {
        return daoQuran.searchQuran(query, translations)
    }

    /**
     * Retrieves all Ayahs in a specific Surah.
     *
     * @param surahId The ID of the Surah.
     * @param translations A list of translation codes to load with the Arabic text.
     * @return A list of [ModelQuran] objects for the specified Surah, with optional translations.
     */
    override fun getQuranForSurah(
        surahId: Int,
        translations: List<String>
    ): List<ModelQuran> {
        return daoQuran.getQuranForSurah(surahId, translations)
    }

    /**
     * Retrieves all Ayahs in a specific Juz.
     *
     * @param juzId The ID of the Juz.
     * @param translations A list of translation codes to load with the Arabic text.
     * @return A list of [ModelQuran] objects for the specified Juz, with optional translations.
     */
    override fun getQuranForJuz(
        juzId: Int,
        translations: List<String>
    ): List<ModelQuran> {
        return daoQuran.getQuranForJuz(juzId, translations)
    }

    /**
     * Retrieves all Ayahs on a specific page.
     *
     * @param pageId The page number in the Quran.
     * @param translations A list of translation codes to load with the Arabic text.
     * @return A list of [ModelQuran] objects for the specified page, with optional translations.
     */
    override fun getQuranForPage(
        pageId: Int,
        translations: List<String>
    ): List<ModelQuran> {
        return daoQuran.getQuranForPage(pageId, translations)
    }

    /**
     * Retrieves a specific Ayah by its unique Ayah ID.
     *
     * @param ayahId The unique ID of the Ayah.
     * @param translations A list of translation codes to load with the Arabic text.
     * @return A [ModelQuran] object for the specified Ayah, or `null` if not found.
     */
    override fun getQuranByAyahId(
        ayahId: Int,
        translations: List<String>
    ): ModelQuran? {
        return daoQuran.getQuranByAyahId(ayahId, translations)
    }

    /**
     * Retrieves a specific Ayah within a specific Surah.
     *
     * @param surahId The ID of the Surah.
     * @param ayahId The number of the Ayah within the Surah.
     * @param translations A list of translation codes to load with the Arabic text.
     * @return A [ModelQuran] object for the specified Surah and Ayah, or `null` if not found.
     */
    override fun getQuranForSurahAndAyah(
        surahId: Int,
        ayahId: Int,
        translations: List<String>
    ): ModelQuran? {
        return daoQuran.getQuranForSurahAndAyah(surahId, ayahId, translations)
    }
}