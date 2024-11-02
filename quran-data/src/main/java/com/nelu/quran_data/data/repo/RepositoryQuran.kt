package com.nelu.quran_data.data.repo

import com.nelu.quran_data.data.model.ModelQuranData
import com.nelu.quran_data.data.repo.base.BaseQuran

class RepositoryQuran : BaseQuran {

    override fun getQuranDataAll(): List<ModelQuranData> {
        TODO("Not yet implemented")
    }

    override fun getQuranDataSurah(surah: Int): List<ModelQuranData> {
        TODO("Not yet implemented")
    }

    override fun getQuranDataJuz(juz: Int): List<ModelQuranData> {
        TODO("Not yet implemented")
    }

    override fun getQuranDataAyah(
        surah: Int,
        ayah: Int
    ): ModelQuranData {
        TODO("Not yet implemented")
    }

    override fun searchQuranData(query: String): List<ModelQuranData> {
        TODO("Not yet implemented")
    }

    override fun searchQuranDataSurah(
        surah: Int,
        query: String
    ): List<ModelQuranData> {
        TODO("Not yet implemented")
    }

    override fun searchQuranDataJuz(
        juz: Int,
        query: String
    ): List<ModelQuranData> {
        TODO("Not yet implemented")
    }
}