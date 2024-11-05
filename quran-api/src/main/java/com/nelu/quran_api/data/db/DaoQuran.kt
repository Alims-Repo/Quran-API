package com.nelu.quran_api.data.db

/**
 * # DaoQuran
 * */
interface DaoQuran {

    /**
     * #### Surah Part
     */
    fun getSurahList(): List<String>

    fun getSurahById(surahId: Int): String?

    fun getSurahForPage(page: Int): List<String>

    fun getSurahForAyah(ayahId: Int): String?

    fun getSurahByName(surahName: String): List<String>


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
    fun getQuranList(translations: List<String>): List<String>

    fun getQuranForSurah(surahId: Int, translations: List<String>): List<String>

    fun getQuranForJuz(juzId: Int, translations: List<String>): List<String>

    fun getQuranForPage(pageId: Int, translations: List<String>): List<String>

    fun getQuranByAyahId(ayahId: Int, translations: List<String>): String?

    fun getQuranForSurahAndAyah(surahId: Int, ayahId: Int, translations: List<String>): String?
}