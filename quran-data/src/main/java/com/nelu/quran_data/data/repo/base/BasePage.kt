package com.nelu.quran_data.data.repo.base

import com.nelu.quran_data.data.model.ModelPage

interface BasePage {

    // Basic API for Page
    fun getPageInfo(): List<ModelPage>

    fun getPageInfo(pageNo: Int): ModelPage?

    // Complex API for Page
    fun getPageInfoByAyah(ayahId: Int): ModelPage?

    fun getPageInfoByAyah(ayahIds: List<Int>): List<ModelPage>
}