package com.nelu.quran_api.binary

import android.content.Context
import android.util.Log
import com.nelu.quran_api.data.model.ModelJuz
import com.nelu.quran_api.data.model.ModelSurah
import java.io.File
import java.io.FileInputStream
import java.nio.ByteBuffer
import java.nio.channels.Channels

open class BinaryJuz(context: Context) : BinaryIndex(context) {

    private val juz = ArrayList<ModelJuz>()

    protected fun juzList(): ArrayList<ModelJuz> {
        if (juz.isNotEmpty())
            return juz

        getIndex().groupBy { it.juz }.mapTo(juz) { (juzNumber, indexList) ->
            val firstItem = indexList.first()
            ModelJuz(
                number = juzNumber,
                startId = firstItem.id,
                startSurah = firstItem.surah,
                totalAyah = indexList.size
            )
        }

        return juz
    }

    protected fun juzForPage(page: Int) : Int {
        return getIndex().find { it.page == page }!!.juz
    }

    protected fun juzForAyah(ayah: Int) : Int {
        return getIndex().find { it.id == ayah }!!.juz
    }
}