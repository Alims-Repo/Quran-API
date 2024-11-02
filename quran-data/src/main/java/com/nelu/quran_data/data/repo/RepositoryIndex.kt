package com.nelu.quran_data.data.repo

import com.nelu.quran_data.data.model.ModelIndex
import com.nelu.quran_data.data.repo.base.BaseIndex
import com.nelu.quran_data.utils.parser.IndexParser.readInfo

class RepositoryIndex : BaseIndex {

    override fun getAll(): List<ModelIndex> {
        return readInfo()
    }

    override fun getById(id: Int): ModelIndex? {
        return readInfo().find { it.id == id }
    }

    override fun getJuz(number: Int): List<ModelIndex> {
        return readInfo().filter { it.juz == number }
    }

    override fun getPage(number: Int): List<ModelIndex> {
        return readInfo().filter { it.page == number }
    }

    override fun getSurah(number: Int): List<ModelIndex> {
        return readInfo().filter { it.surah == number }
    }

    override fun getById(
        surah: Int,
        ayah: Int
    ): ModelIndex? {
        return readInfo().find { it.surah == surah && it.ayahInSurah == ayah }
    }
}