package com.nelu.quran_api.data.db.dao

import com.nelu.quran_api.data.model.ModelQuran
import com.nelu.quran_api.data.model.ModelSurah

/**
 * # DaoQuran
 * */
internal interface DaoSurah {

    fun getSurahList(): List<ModelSurah>

    fun getSurahById(surahId: Int): ModelSurah?

    fun getSurahForPage(page: Int): List<ModelSurah>

    fun getSurahForAyah(ayahId: Int): ModelSurah?

    fun getSurahByName(surahName: String): List<ModelSurah>



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