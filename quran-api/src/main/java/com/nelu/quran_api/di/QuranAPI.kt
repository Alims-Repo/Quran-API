package com.nelu.quran_api.di

import android.app.Application
import com.nelu.quran_api.data.db.ImplJuz
import com.nelu.quran_api.data.db.ImplQuran
import com.nelu.quran_api.data.db.dao.DaoSurah
import com.nelu.quran_api.data.db.ImplSurah
import com.nelu.quran_api.data.db.ImplTranslation
import com.nelu.quran_api.data.db.dao.DaoJuz
import com.nelu.quran_api.data.db.dao.DaoQuran
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
 * ### QuranAPI
 *
 * A singleton object that provides access to Quran data and related repositories.
 * This API acts as the main entry point for interacting with the Quran data within the application,
 * offering functionalities for querying Surahs, Juz, Pages, Quran content, and Translations.
 *
 * Implements `BaseAPI` to enforce consistent structure across different data modules.
 */
object QuranAPI : BaseAPI {

    // DAO (Data Access Object) for interacting with the database.
    private lateinit var daoJuz: DaoJuz
    private lateinit var daoSurah: DaoSurah
    private lateinit var daoQuran: DaoQuran
    private lateinit var daoTranslation: DaoTranslation

    // Repositories for each specific section of Quran data.
    private lateinit var juz: BaseJuz
    private lateinit var page: BasePage
    private lateinit var surah: BaseSurah
    private lateinit var quran: BaseQuran
    private lateinit var translation: BaseTranslation

    // Properties to access each section of the Quran data. These are overridden from BaseAPI.
    override val JUZ: BaseJuz get() = juz
    override val PAGE: BasePage get() = page
    override val SURAH: BaseSurah get() = surah
    override val QURAN: BaseQuran get() = quran
    override val TRANSLATION: BaseTranslation get() = translation

    /**
     * Initializes the QuranAPI with the application context and configures each repository.
     * This function should be called once during the application setup phase.
     *
     * @param application The application context used for database initialization.
     */
    fun init(app: Application) {
        app.run {
            applicationContext.restoreData()

            daoJuz = ImplJuz(this)
            daoSurah = ImplSurah(this)
            daoQuran = ImplQuran(this)
            daoTranslation = ImplTranslation(this)
        }

        // Initialize the data access object for Quran data.


        // Configure each repository with the DAO instance for data access.
        juz = RepositoryJuz(daoJuz)
        page = RepositoryPage(daoSurah)
        surah = RepositorySurah(daoSurah)
        quran = RepositoryQuran(daoQuran)
        translation = RepositoryTranslation(daoTranslation)
    }
}