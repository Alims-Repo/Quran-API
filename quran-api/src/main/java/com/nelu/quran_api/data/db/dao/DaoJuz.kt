package com.nelu.quran_api.data.db.dao

import com.nelu.quran_api.data.model.ModelJuz

interface DaoJuz {

    fun getJuzList(): List<ModelJuz>

    fun getJuzById(juzId: Int): ModelJuz?

    fun getJuzForPage(page: Int): ModelJuz

    fun getJuzForAyah(ayahId: Int): ModelJuz?
}