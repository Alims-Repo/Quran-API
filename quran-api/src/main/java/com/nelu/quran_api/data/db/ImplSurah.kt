package com.nelu.quran_api.data.db

import android.app.Application
import com.nelu.quran_api.binary.BinarySurah
import com.nelu.quran_api.data.db.dao.DaoSurah
import com.nelu.quran_api.data.model.ModelSurah

/**
 * # ImplSurah
 *
 * This class implements the [DaoSurah] interface by extending [BinarySurah] to provide access
 * to Surah-related data in the Quran. It enables retrieval of Surahs by various criteria,
 * including Surah ID, page number, Ayah ID, and Surah name, offering efficient and flexible
 * access to Quranic chapter information.
 *
 * @param application The application context, used for accessing resources and file storage.
 */
class ImplSurah(
    application: Application
) : BinarySurah(application), DaoSurah {

    /**
     * Retrieves a list of all Surahs in the Quran.
     *
     * @return A list of [ModelSurah] objects representing each Surah in the Quran.
     */
    override fun getSurahList(): List<ModelSurah> {
        return surahList().toList()
    }

    /**
     * Finds a specific Surah by its Surah ID.
     *
     * @param surahId The unique identifier (1 to 114) of the Surah to retrieve.
     * @return A [ModelSurah] object for the specified Surah, or `null` if not found.
     */
    override fun getSurahById(surahId: Int): ModelSurah? {
        return surahById(surahId)
    }

    /**
     * Retrieves all Surahs that start on a specific page.
     *
     * @param page The page number in the Quran.
     * @return A list of [ModelSurah] objects representing the Surahs that start on the specified page.
     */
    override fun getSurahForPage(page: Int): List<ModelSurah> {
        return getIndex()
            .filter { it.page == page }
            .distinctBy { it.surah }
            .map { it.surah }
            .let { surahIds ->
                if (surahIds.size == 1)
                    listOf(surahById(surahIds.first())!!)
                else surahList().filter {
                    surahIds.contains(it.number)
                }
            }
    }

    /**
     * Finds the Surah that contains the specified Ayah (verse) ID.
     *
     * @param ayahId The unique ID of the Ayah.
     * @return A [ModelSurah] object representing the Surah containing the given Ayah, or `null` if not found.
     */
    override fun getSurahForAyah(ayahId: Int): ModelSurah? {
        return if (ayahId in 1..6236)
            surahById(getIndex().find { it.id == ayahId }!!.surah)
        else null
    }

    /**
     * Retrieves all Surahs that match a specific name.
     *
     * @param surahName The name or related attribute of the Surah to search for, either in Arabic or English.
     * @return A list of [ModelSurah] objects that match the given Surah name or related attribute.
     */
    override fun getSurahByName(surahName: String): List<ModelSurah> {
        return surahList().filter {
            it.englishName.contains(surahName, ignoreCase = true)
                    || it.arabicName.contains(surahName, ignoreCase = true)
                    || it.revelationType.contains(surahName, ignoreCase = true)
                    || it.englishTranslation.contains(surahName, ignoreCase = true)
        }
    }

}