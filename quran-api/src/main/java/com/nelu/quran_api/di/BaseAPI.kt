package com.nelu.quran_api.di

import android.content.Context
import com.nelu.quran_api.R
import com.nelu.quran_api.data.repository.base.BaseAudio
import com.nelu.quran_api.data.repository.base.BaseJuz
import com.nelu.quran_api.data.repository.base.BasePage
import com.nelu.quran_api.data.repository.base.BaseQuran
import com.nelu.quran_api.data.repository.base.BaseSurah
import com.nelu.quran_api.data.repository.base.BaseTranslation
import java.io.File
import java.io.FileOutputStream

/**
 * # BaseAPI
 *
 * Interface for accessing repository components that provide Quranic data such as Juz, Page, Surah,
 * Quran, and Translation. This interface establishes a unified access point for different sections
 * of Quranic data through their respective repositories.
 */
interface BaseAPI {

    /** Provides access to Juz-related data and operations, implemented by [BaseJuz]. */
    val JUZ: BaseJuz

    /** Provides access to Page-related data and operations, implemented by [BasePage]. */
    val PAGE: BasePage

    /** Provides access to Surah-related data and operations, implemented by [BaseSurah]. */
    val SURAH: BaseSurah

    /** Provides access to Quran-related data and operations, implemented by [BaseQuran]. */
    val QURAN: BaseQuran

    val AUDIO: BaseAudio

    /** Provides access to Translation-related data and operations, implemented by [BaseTranslation]. */
    val TRANSLATION: BaseTranslation

    /**
     * Restores necessary Quranic data files from raw resources to the device’s internal storage.
     *
     * This function checks if essential data files (such as `surahs.dat`, `arabic.dat`, `indexes.dat`,
     * and `translator.dat`) exist in the internal storage. If any file is missing, it copies the file
     * from the application's raw resources to internal storage.
     *
     * @receiver [Context] The application context used for accessing resources and file storage.
     */
    fun Context.restoreData() {
        listOf(
            "surahs.dat" to R.raw.surahs,
            "arabic.dat" to R.raw.arabic,
            "indexes.dat" to R.raw.indexes,
            "translator.dat" to R.raw.translator,
        ).forEach { (fileName, resourceId) ->
            val file = File(filesDir, fileName)
            if (!file.exists()) {
                resources.openRawResource(resourceId).use { inputStream ->
                    FileOutputStream(file).use { outputStream ->
                        inputStream.copyTo(outputStream)
                    }
                }
            }
        }
    }
}