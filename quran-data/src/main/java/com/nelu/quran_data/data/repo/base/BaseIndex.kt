package com.nelu.quran_data.data.repo.base

import com.nelu.quran_data.data.model.ModelIndex
import com.nelu.quran_data.data.model.ModelSurah

interface BaseIndex {

    fun getAll() : List<ModelIndex>

    fun getById(id: Int) : ModelIndex?

    fun getJuz(number: Int) : List<ModelIndex>

    fun getPage(number: Int) : List<ModelIndex>

    fun getSurah(number: Int) : List<ModelIndex>

    fun getById(surah: Int, ayah: Int) : ModelIndex?
}