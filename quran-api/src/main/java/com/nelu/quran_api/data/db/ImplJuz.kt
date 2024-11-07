package com.nelu.quran_api.data.db

import android.app.Application
import com.nelu.quran_api.binary.BinaryJuz
import com.nelu.quran_api.data.db.dao.DaoJuz
import com.nelu.quran_api.data.model.ModelJuz

/**
 * # ImplJuz
 *
 * This class implements the [DaoJuz] interface by extending [BinaryJuz] to provide access
 * to Juz-related data in the Quran. It offers methods to retrieve a list of Juz, find a specific
 * Juz by its ID, and locate the Juz that corresponds to a given page or Ayah (verse) ID.
 *
 * By using binary data for efficient storage and retrieval, `ImplJuz` enables optimized access
 * to Juz information within the application.
 *
 * @param application The application context, used for accessing resources and file storage.
 */
class ImplJuz(
    application: Application
) : BinaryJuz(application), DaoJuz {

    /**
     * Retrieves a list of all Juz in the Quran.
     *
     * @return A list of [ModelJuz] objects representing each Juz in the Quran.
     */
    override fun getJuzList(): List<ModelJuz> {
        return juzList()
    }

    /**
     * Finds a specific Juz by its Juz ID.
     *
     * @param juzId The unique ID of the Juz (1 to 30).
     * @return A [ModelJuz] object representing the specified Juz, or `null` if not found.
     */
    override fun getJuzById(juzId: Int): ModelJuz? {
        return juzList().find { it.number == juzId }
    }

    /**
     * Finds the Juz associated with a specific page number.
     *
     * @param page The page number in the Quran.
     * @return A [ModelJuz] object representing the Juz for the given page, or `null` if not found.
     */
    override fun getJuzForPage(page: Int): ModelJuz? {
        return juzList().find { it.number == juzForPage(page) }
    }

    /**
     * Finds the Juz associated with a specific Ayah ID.
     *
     * @param ayahId The unique ID of the Ayah.
     * @return A [ModelJuz] object representing the Juz for the given Ayah, or `null` if not found.
     */
    override fun getJuzForAyah(ayahId: Int): ModelJuz? {
        return juzList().find { it.number == juzForAyah(ayahId) }
    }
}