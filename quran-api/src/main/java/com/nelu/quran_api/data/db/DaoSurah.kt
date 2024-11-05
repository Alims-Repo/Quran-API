package com.nelu.quran_api.data.db

import com.nelu.quran_api.data.model.ModelQuran
import com.nelu.quran_api.data.model.ModelSurah

/**
 * # DaoQuran
 * */
interface DaoSurah {

    /**
     * #### Surah Part
     */
    fun getSurahList(): List<ModelSurah>

    fun getSurahById(surahId: Int): ModelSurah?

    fun getSurahForPage(page: Int): List<ModelSurah>

    fun getSurahForAyah(ayahId: Int): ModelSurah?

    fun getSurahByName(surahName: String): List<ModelSurah>


    /**
     * #### Juz Part
     */
    fun getJuzList(): List<String>

    fun getJuzById(juzId: Int): String?

    fun getJuzForPage(page: Int): String

    fun getJuzForAyah(ayahId: Int): String?


    /**
     * #### Page Part
     */
    fun getPageList(): List<String>

    fun getPageByNumber(pageId: Int): String?

    fun getPageForAyah(ayahId: Int): String?


    /**
     * #### Translation Part
     */
    fun getTranslationList(): List<String>


    /**
     * #### Quran Part
     */
    fun getQuranList(translations: List<String>): List<ModelQuran>

    fun getQuranForSurah(surahId: Int, translations: List<String>): List<ModelQuran>

    fun getQuranForJuz(juzId: Int, translations: List<String>): List<ModelQuran>

    fun getQuranForPage(pageId: Int, translations: List<String>): List<ModelQuran>

    fun getQuranByAyahId(ayahId: Int, translations: List<String>): ModelQuran?

    fun getQuranForSurahAndAyah(surahId: Int, ayahId: Int, translations: List<String>): ModelQuran?
}