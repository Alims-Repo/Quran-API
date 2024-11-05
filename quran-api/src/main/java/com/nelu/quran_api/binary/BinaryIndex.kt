package com.nelu.quran_api.binary

import android.content.Context
import com.nelu.quran_api.data.model.ModelIndex
import com.nelu.quran_api.utils.NativeUtils.readModelIndexListFromFileDescriptor
import java.io.File
import java.io.FileInputStream

open class BinaryIndex(private val context: Context) {

    private val index = ArrayList<ModelIndex>()

    protected fun getIndexByPage(page: Int) : List<ModelIndex> {
        if (index.isEmpty()) loadIndex()
        return index.filter { it.page == page }
    }

    protected fun getIndexByAyah(ayah: Int) : ModelIndex? {
        if (index.isEmpty()) loadIndex()
        return index.find { it.id == ayah }
    }

    private fun loadIndex() {
        val cacheFile = File(context.filesDir, "indexes.dat")
        val flatArray =  readModelIndexListFromFileDescriptor(
            FileInputStream(cacheFile).fd, cacheFile.length()
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
    }
}