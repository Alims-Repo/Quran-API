package com.nelu.quran_api.di

import android.app.Application
import com.nelu.quran_api.data.db.ImplJuz
import com.nelu.quran_api.data.db.ImplQuran
import com.nelu.quran_api.data.db.ImplSurah
import com.nelu.quran_api.data.db.ImplTranslation
import com.nelu.quran_api.data.db.dao.DaoJuz
import com.nelu.quran_api.data.db.dao.DaoQuran
import com.nelu.quran_api.data.db.dao.DaoSurah
import com.nelu.quran_api.data.db.dao.DaoTranslation
import com.nelu.quran_api.data.repository.RepositoryJuz
import com.nelu.quran_api.data.repository.RepositoryPage
import com.nelu.quran_api.data.repository.RepositoryQuran
import com.nelu.quran_api.data.repository.RepositorySurah
import com.nelu.quran_api.data.repository.RepositoryTranslation
import com.nelu.quran_api.data.repository.base.BaseJuz
import com.nelu.quran_api.data.repository.base.BasePage
import com.nelu.quran_api.data.repository.base.BaseQuran
import com.nelu.quran_api.data.repository.base.BaseSurah
import com.nelu.quran_api.data.repository.base.BaseTranslation

/**
 * # QuranAPI
 *
 * A singleton object that provides access to Quranic data repositories by implementing the [BaseAPI] interface.
 * This object initializes and manages repository instances for Juz, Page, Surah, Quran, and Translation data,
 * using DAO implementations for data access. It must be initialized using the [init] method.
 */
object QuranAPI : BaseAPI {

    // DAO instances for accessing data
    private lateinit var daoJuz: DaoJuz
    private lateinit var daoSurah: DaoSurah
    private lateinit var daoQuran: DaoQuran
    private lateinit var daoTranslation: DaoTranslation

    // Repository instances for providing data operations
    private lateinit var juz: BaseJuz
    private lateinit var page: BasePage
    private lateinit var surah: BaseSurah
    private lateinit var quran: BaseQuran
    private lateinit var translation: BaseTranslation

    /** Provides access to Juz-related data and operations */
    override val JUZ: BaseJuz get() = juz

    /** Provides access to Page-related data and operations */
    override val PAGE: BasePage get() = page

    /** Provides access to Surah-related data and operations */
    override val SURAH: BaseSurah get() = surah

    /** Provides access to Quran-related data and operations */
    override val QURAN: BaseQuran get() = quran

    /** Provides access to Translation-related data and operations */
    override val TRANSLATION: BaseTranslation get() = translation

    /**
     * Initializes the QuranAPI object with necessary repository and DAO instances.
     *
     * This function should be called once at application startup to initialize the repositories
     * and ensure that all necessary data files are available in internal storage. It configures
     * each repository with DAO instances for accessing Quranic data.
     *
     * @param app The application instance, used for context and file operations.
     */
    fun init(app: Application) {
        app.run {
            applicationContext.restoreData()

            // Initialize DAOs with the application context
            daoJuz = ImplJuz(this)
            daoSurah = ImplSurah(this)
            daoQuran = ImplQuran(this)
            daoTranslation = ImplTranslation(this)
        }

        // Configure each repository with the DAO instance for data access
        juz = RepositoryJuz(daoJuz)
        page = RepositoryPage(daoSurah)
        surah = RepositorySurah(daoSurah)
        quran = RepositoryQuran(daoQuran)
        translation = RepositoryTranslation(app, daoTranslation)
    }
}