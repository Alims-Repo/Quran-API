package com.nelu.quran_api.di

import android.app.Application
import com.nelu.quran_api.data.repository.base.BaseJuz
import com.nelu.quran_api.data.repository.base.BasePage
import com.nelu.quran_api.data.repository.base.BaseQuran
import com.nelu.quran_api.data.repository.base.BaseSurah
import com.nelu.quran_api.data.repository.base.BaseTranslation

/**
 * # Base API Interface
 */
interface BaseAPI {

    var application: Application

    val JUZ: BaseJuz

    val PAGE: BasePage

    val SURAH: BaseSurah

    val QURAN: BaseQuran

    val TRANSLATION: BaseTranslation
}