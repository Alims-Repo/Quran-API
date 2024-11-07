package com.nelu.quran_api.data.repository

import com.nelu.quran_api.data.db.dao.DaoJuz
import com.nelu.quran_api.data.model.ModelJuz
import com.nelu.quran_api.data.repository.base.BaseJuz

/**
 * # RepositoryJuz
 *
 * This class implements the [BaseJuz] interface by delegating Juz-related data operations
 * to the [DaoJuz] data access object. It provides structured methods for retrieving Juz
 * information, allowing access to a list of Juz, a specific Juz by its ID, and finding
 * a Juz by page number or Ayah (verse) ID.
 *
 * @param daoJuz The Data Access Object (DAO) that handles Juz-related data operations.
 */
class RepositoryJuz(
    private val daoJuz: DaoJuz
) : BaseJuz {

    /**
     * Retrieves a list of all Juz in the Quran.
     *
     * @return A list of [ModelJuz] objects representing each Juz in the Quran.
     */
    override fun getJuzList(): List<ModelJuz> {
        return daoJuz.getJuzList()
    }

    /**
     * Finds a specific Juz by its unique Juz ID.
     *
     * @param juzId The unique identifier (1 to 30) of the Juz to retrieve.
     * @return A [ModelJuz] object for the specified Juz, or `null` if not found.
     */
    override fun getJuzById(juzId: Int): ModelJuz? {
        return daoJuz.getJuzById(juzId)
    }

    /**
     * Retrieves the Juz associated with a specific page number.
     *
     * @param page The page number in the Quran.
     * @return A [ModelJuz] object representing the Juz for the given page, or `null` if no Juz is found.
     */
    override fun getJuzForPage(page: Int): ModelJuz? {
        return daoJuz.getJuzForPage(page)
    }

    /**
     * Finds the Juz that contains the specified Ayah (verse) ID.
     *
     * @param ayahId The unique ID of the Ayah.
     * @return A [ModelJuz] object representing the Juz for the given Ayah, or `null` if no Juz is found.
     */
    override fun getJuzForAyah(ayahId: Int): ModelJuz? {
        return daoJuz.getJuzForAyah(ayahId)
    }
}