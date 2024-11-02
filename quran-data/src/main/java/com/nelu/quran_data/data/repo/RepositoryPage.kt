package com.nelu.quran_data.data.repo

import com.nelu.quran_data.data.model.ModelPage
import com.nelu.quran_data.data.repo.base.BasePage
import com.nelu.quran_data.utils.parser.PageInfoParser.readPageInfo

class RepositoryPage : BasePage {

    override fun getPageInfo(): List<ModelPage> {
        return readPageInfo()
    }

    override fun getPageInfo(pageNo: Int): ModelPage? {
        return readPageInfo(pageNo)
    }

    override fun getPageInfoByAyah(ayahId: Int): ModelPage? {
        return readPageInfo().find {
            it.start <= ayahId && it.end >= ayahId
        }
    }

    override fun getPageInfoByAyah(ayahIds: List<Int>): List<ModelPage> {
        return readPageInfo().apply {
            ArrayList<ModelPage>().also {
                ayahIds.forEach { ayahId ->
                    it.add(find { it.end >= ayahId }!!)
                }
            }
        }
    }
}