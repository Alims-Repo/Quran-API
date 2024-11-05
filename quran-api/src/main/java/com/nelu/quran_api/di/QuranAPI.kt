package com.nelu.quran_api.di

import android.app.Application
import com.nelu.quran_api.data.db.DaoQuran
import com.nelu.quran_api.data.db.DatabaseQuran
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

object QuranAPI : BaseAPI {

    private lateinit var daoQuran: DaoQuran

   private lateinit var juz: BaseJuz
   private lateinit var page: BasePage
   private lateinit var surah: BaseSurah
   private lateinit var quran: BaseQuran
   private lateinit var translation: BaseTranslation


    override lateinit var application: Application

    override val JUZ: BaseJuz get() = juz
    override val PAGE: BasePage get() = page
    override val SURAH: BaseSurah get() = surah
    override val QURAN: BaseQuran get() = quran
    override val TRANSLATION: BaseTranslation get() = translation

    fun init(application: Application) {
        this.application = application

        daoQuran = DatabaseQuran(application)

        juz = RepositoryJuz(daoQuran)
        page = RepositoryPage(daoQuran)
        surah = RepositorySurah(daoQuran)
        quran = RepositoryQuran(daoQuran)
        translation = RepositoryTranslation(daoQuran)
    }
}