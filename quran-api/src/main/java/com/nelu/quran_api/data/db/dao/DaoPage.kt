package com.nelu.quran_api.data.db.dao

import com.nelu.quran_api.data.model.ModelIndex

interface DaoPage {

    fun getPageList(): List<ModelIndex>

    fun getPageByNumber(pageId: Int): ModelIndex?

    fun getPageForAyah(ayahId: Int): ModelIndex?
}