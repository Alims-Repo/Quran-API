package com.nelu.quran_data.di

import android.app.Application
import com.nelu.quran_data.data.constant.Page
import com.nelu.quran_data.data.repo.RepositoryJuz
import com.nelu.quran_data.data.repo.RepositoryPage
import com.nelu.quran_data.data.repo.RepositoryQuran
import com.nelu.quran_data.data.repo.RepositorySurah
import com.nelu.quran_data.data.repo.RepositoryTranslations
import com.nelu.quran_data.data.repo.base.BaseJuz
import com.nelu.quran_data.data.repo.base.BasePage
import com.nelu.quran_data.data.repo.base.BaseQuran
import com.nelu.quran_data.data.repo.base.BaseSurah
import com.nelu.quran_data.data.repo.base.BaseTranslations

/**
 * Singleton object `QuranData` serves as a centralized source for accessing
 * various components of the Quran data, including Surahs, Juz, Translations,
 * and the Quran itself. This object implements the `BaseData` interface,
 * ensuring that each component is accessible via the overridden properties
 * `surah`, `juz`, `translation` and `quran`.
 *
 * The object initializes each component (Surah, Juz, Translations, Quran)
 * with its respective repository implementation in the `init` block, providing
 * a structured and convenient way to retrieve Quranic data.
 *
 * @property surah Returns an instance of `BaseSurah`, initialized through `RepositorySurah`.
 * @property juz Returns an instance of `BaseJuz`, initialized through `RepositoryJuz`.
 * @property translation Returns an instance of `BaseTranslations`, initialized through `RepositoryTranslations`.
 * @property quran Returns an instance of `BaseQuran`, initialized through `RepositoryQuran`.
 */
object QuranData : BaseData {

    lateinit var context: Application

    private var baseSurah: BaseSurah? = null
    private var baseJuz: BaseJuz? = null
    private var basePage: BasePage? = null
    private var baseTranslations: BaseTranslations? = null
    private var baseQuran: BaseQuran? = null


    override val surah: BaseSurah get() = baseSurah!!
    override val juz: BaseJuz get() = baseJuz!!
    override val page: BasePage get() = basePage!!
    override val translation: BaseTranslations get() = baseTranslations!!
    override val quran: BaseQuran get() = baseQuran!!


    /**
     * Initialization block for the `QuranData` singleton.
     *
     * This block assigns instances of repository classes to the respective
     * properties (`baseSurah`, `baseJuz`, `baseTranslations`, `baseQuran`).
     * Each repository instance provides the actual data source for the Quranic
     * components. By initializing these components here, `QuranData` ensures that
     * each property is ready for use immediately upon object creation.
     *
     * - `baseSurah`: Assigned to `RepositorySurah`, providing data for Surahs.
     * - `baseJuz`: Assigned to `RepositoryJuz`, handling data for Juz sections.
     * - `baseTranslations`: Assigned to `RepositoryTranslations`, managing translations.
     * - `baseQuran`: Assigned to `RepositoryQuran`, offering Quranic content.
     */
    fun init(context: Application) {
        this.context = context
        baseSurah = RepositorySurah()
        basePage = RepositoryPage()
        baseJuz = RepositoryJuz(surah)
        baseTranslations = RepositoryTranslations()
        baseQuran = RepositoryQuran()

        // Preload Data
        Page
    }
}