package com.nelu.quran_api.data.repository.base

interface BasePage {

    fun getPageList(): List<String>

    fun getPageByNumber(pageId: Int): String?

    fun getPageForAyah(ayahId: Int): String?
}