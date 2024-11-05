package com.nelu.quran_api.binary

import android.content.Context
import com.nelu.quran_api.data.model.ModelIndex
import com.nelu.quran_api.utils.NativeUtils.readModelIndexListFromFileDescriptor
import java.io.File
import java.io.FileInputStream

open class BinaryIndex(context: Context) {

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

        val flatArray =  readModelIndexListFromFileDescriptor(
            FileInputStream(indexes).fd, indexes.length()
        )

        for (i in flatArray.indices step 5) {
            index.add(
                ModelIndex(
                    id = flatArray[i],
                    surah = flatArray[i + 1],
                    juz = flatArray[i + 2],
                    page = flatArray[i + 3],
                    ayahInSurah = flatArray[i + 4]
                )
            )
        }

        return index
    }
}