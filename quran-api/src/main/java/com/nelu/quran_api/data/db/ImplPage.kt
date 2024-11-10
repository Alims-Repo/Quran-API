package com.nelu.quran_api.data.db

import android.content.Context
import com.nelu.quran_api.data.db.dao.DaoPage
import com.nelu.quran_api.data.model.ModelIndex

class ImplPage(context: Context) : DaoPage {

    override fun getPageList(): List<ModelIndex> {
        TODO("Not yet implemented")
    }

    override fun getPageByNumber(pageId: Int): ModelIndex? {
        TODO("Not yet implemented")
    }

    override fun getPageForAyah(ayahId: Int): ModelIndex? {
        TODO("Not yet implemented")
    }
}