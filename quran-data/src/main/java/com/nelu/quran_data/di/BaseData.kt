package com.nelu.quran_data.di

import com.nelu.quran_data.data.repo.base.BaseJuz
import com.nelu.quran_data.data.repo.base.BasePage
import com.nelu.quran_data.data.repo.base.BaseQuran
import com.nelu.quran_data.data.repo.base.BaseSurah
import com.nelu.quran_data.data.repo.base.BaseTranslations

interface BaseData {

    val surah: BaseSurah

    val juz: BaseJuz

    val page : BasePage

    val translation: BaseTranslations

    val quran: BaseQuran

}