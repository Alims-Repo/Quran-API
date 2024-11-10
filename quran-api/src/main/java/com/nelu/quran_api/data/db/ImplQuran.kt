package com.nelu.quran_api.data.db

import android.app.Application
import com.nelu.quran_api.binary.BinaryQuran
import com.nelu.quran_api.data.db.dao.DaoQuran
import com.nelu.quran_api.data.model.ModelQuran

/**
 * # ImplQuran
 *
 * This class implements the [DaoQuran] interface by extending [BinaryQuran] to provide access
 * to Quranic data. It allows for efficient retrieval and searching of Ayahs (verses) in the Quran,
 * with optional translations. `ImplQuran` offers methods to retrieve Ayahs based on Surah, Juz, page,
 * Ayah ID, and by search query.
 *
 * @param application The application context, used for accessing resources and file storage.
 */
class ImplQuran(
    application: Application
) : BinaryQuran(application), DaoQuran {

    /**
     * Retrieves the entire list of Quranic Ayahs, optionally including translations.
     *
     * @param translations A list of translation codes to load with the Arabic text.
     * @return A list of [ModelQuran] objects representing the Quran with optional translations.
     */
    override fun getQuranList(
        translations: List<String>
    ): List<ModelQuran> {
        return quranList(translations)
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
        val lowerCaseQuery = query.lowercase()

        return getQuranList(translations).filter { model ->
            model.arabic.lowercase().contains(lowerCaseQuery)
                    || model.translation.any { translationText ->
                translationText.lowercase().contains(lowerCaseQuery)
            }
        }
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
        return quranList(translations).filter { it.surah == surahId }
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
        return quranList(translations).filter { it.juz == juzId }
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
        return quranList(translations).filter { it.page == pageId }
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
        return quranList(translations).find { it.id == ayahId }
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
        return quranList(translations).find {
            it.surah == surahId && it.ayahInSurah == ayahId
        }
    }

    /**
     * # ModelSearchQuran
     *
     * A data model to facilitate efficient search operations by storing Quranic Ayah content
     * in binary format. Each instance of `ModelSearchQuran` represents an Ayah with its Arabic
     * text and translations stored as byte arrays.
     *
     * @property id The unique ID of the Ayah.
     * @property arabicBinary A byte array representing the binary form of the Arabic text.
     * @property translationBinary A list of byte arrays for the translations of the Ayah.
     */
    data class ModelSearchQuran(
        val id: Int,
        val arabicBinary: ByteArray,
        val translationBinary: List<ByteArray>
    )
}