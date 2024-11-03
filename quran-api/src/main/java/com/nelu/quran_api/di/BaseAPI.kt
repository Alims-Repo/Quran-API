package com.nelu.quran_api.di

/**
 * # Base API Interface
 */
interface BaseAPI {

    /**
     * ### Surah Part
     */
    fun getSurahList() : List<String>

    fun getSurahById(surahId: Int) : String?

    fun getSurahByPage(page: Int) : List<String>

    fun getSurahByAyah(ayahId: Int) : String?

    fun getSurahByName(surahName: String) : List<String>


    /**
     * ### Juz Part
     */
    fun getJuzList() : List<String>

    fun getJuzById(juzId: Int) : String?

    fun getJuzByPage(page: Int) : String

    fun getJuzByAyah(ayahId: Int) : String?


    /**
     * ### Page Part
     */
    fun getPageList() : List<String>

    fun getPageByNumber(pageId: Int) : String?

    fun getPageByAyah(ayahId: Int) : String?


    /**
     * ### Translation Part
     */
    fun getTranslationList() : List<String>


    /**
     * ### Quran Part
     */
    fun getQuranList(translations: List<String>) : List<String>

    fun getQuranBySurah(surahId: Int, translations: List<String>) : List<String>

    fun getQuranByJuz(juzId: Int, translations: List<String>) : List<String>

    fun getQuranByPage(pageId: Int, translations: List<String>) : List<String>

    fun getQuranById(ayahId: Int, translations: List<String>) : String?

    fun getQuranBySurahAndAyah(surahId: Int, ayahId: Int, translations: List<String>) : String?

}