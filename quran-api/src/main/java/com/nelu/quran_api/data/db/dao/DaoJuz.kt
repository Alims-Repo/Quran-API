package com.nelu.quran_api.data.db.dao

internal interface DaoJuz {

    fun getJuzList(): List<String>

    fun getJuzById(juzId: Int): String?

    fun getJuzForPage(page: Int): String

    fun getJuzForAyah(ayahId: Int): String?

}