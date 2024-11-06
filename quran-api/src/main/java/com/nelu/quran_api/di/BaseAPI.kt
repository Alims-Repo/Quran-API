package com.nelu.quran_api.di

import android.content.Context
import com.nelu.quran_api.data.repository.base.BaseJuz
import com.nelu.quran_api.data.repository.base.BasePage
import com.nelu.quran_api.data.repository.base.BaseQuran
import com.nelu.quran_api.data.repository.base.BaseSurah
import com.nelu.quran_api.data.repository.base.BaseTranslation
import java.io.File
import com.nelu.quran_api.R
import java.io.FileOutputStream

/**
 * # Base API Interface
 *
 * A foundational interface for Quran-related API components, establishing a consistent structure
 * and access pattern across various data sections (Juz, Page, Surah, Quran, Translation).
 * Implementations of this interface provide an organized access point for querying Quranic data.
 */
interface BaseAPI {

    /**
     * The application context for initializing resources or components dependent on application scope.
     */

    /**
     * Provides access to Juz-related data and operations.
     * The Juz repository or data module should implement `BaseJuz`.
     */
    val JUZ: BaseJuz

    /**
     * Provides access to Page-related data and operations.
     * The Page repository or data module should implement `BasePage`.
     */
    val PAGE: BasePage

    /**
     * Provides access to Surah-related data and operations.
     * The Surah repository or data module should implement `BaseSurah`.
     */
    val SURAH: BaseSurah

    /**
     * Provides access to Quran-related data and operations.
     * The Quran repository or data module should implement `BaseQuran`.
     */
    val QURAN: BaseQuran

    /**
     * Provides access to Translation-related data and operations.
     * The Translation repository or data module should implement `BaseTranslation`.
     */
    val TRANSLATION: BaseTranslation

    fun Context.restoreData() {
        listOf(
            "surahs.dat" to R.raw.surahs,
            "arabic.dat" to R.raw.arabic,
            "en_sahih.dat" to R.raw.en_sahih,
            "indexes.dat" to R.raw.indexes,
            "translator.dat" to R.raw.translator,
        ).forEach {
            if (!File(filesDir, it.first).exists()) {
                resources.openRawResource(it.second).copyTo(
                    FileOutputStream(File(filesDir, it.first))
                )
            }
        }
    }
}