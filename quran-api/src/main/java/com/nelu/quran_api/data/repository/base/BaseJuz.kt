package com.nelu.quran_api.data.repository.base

import com.nelu.quran_api.data.model.ModelJuz

interface BaseJuz {

    fun getJuzList(): List<ModelJuz>

    fun getJuzById(juzId: Int): ModelJuz?

    fun getJuzForPage(page: Int): ModelJuz?

    fun getJuzForAyah(ayahId: Int): ModelJuz?
}