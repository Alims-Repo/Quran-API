package com.nelu.quran_api.binary

import android.content.Context
import android.util.Log
import com.nelu.quran_api.data.model.ModelIndex
import com.nelu.quran_api.utils.NativeUtils.readModelIndexListFromFileDescriptor
import java.io.File
import java.io.FileInputStream

open class BinaryIndex(context: Context) {

    private lateinit var rawIndex : IntArray

    private val index = ArrayList<ModelIndex>()

    private val indexes = File(context.filesDir, "indexes.dat")

//    protected fun getIndexByPage(page: Int) : List<ModelIndex> {
//        if (index.isEmpty()) loadIndex()
//        return index.filter { it.page == page }
//    }
//
//    protected fun getIndexByAyah(ayah: Int) : ModelIndex? {
//        if (index.isEmpty()) loadIndex()
//        return index.find { it.id == ayah }
//    }
//
//    protected fun getIndexByJuz(page: Int) : List<ModelIndex> {
//        if (index.isEmpty()) loadIndex()
//        return index.filter { it.page == page }
//    }

    protected fun getIndex() : List<ModelIndex> {
        if (index.isNotEmpty())
            return index

        for (i in getRawIndex().indices step 5) {
            index.add(
                ModelIndex(
                    id = rawIndex[i],
                    surah = rawIndex[i + 1],
                    juz = rawIndex[i + 2],
                    page = rawIndex[i + 3],
                    ayahInSurah = rawIndex[i + 4]
                )
            )
        }

        return index
    }

    protected fun getRawIndex() : IntArray {
        if (::rawIndex.isInitialized.not())
            rawIndex = readModelIndexListFromFileDescriptor(
                FileInputStream(indexes).fd, indexes.length()
            )

        return rawIndex
    }
}