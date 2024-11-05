package com.nelu.quran_api.binary

import android.content.Context
import com.nelu.quran_api.data.model.ModelJuz
import com.nelu.quran_api.data.model.ModelSurah
import java.io.File
import java.io.FileInputStream
import java.nio.ByteBuffer
import java.nio.channels.Channels

open class BinaryJuz(context: Context) : BinaryIndex(context) {

    private val juz = ArrayList<ModelJuz>()

    protected fun juzList() : ArrayList<ModelJuz> {
        if (juz.isNotEmpty()) return juz

        juz.addAll(
            getIndex().distinctBy { it.juz }.map {
                ModelJuz(
                    it.juz,
                    it.id,
                    it.surah,
                    it.ayahInSurah
                )
            }
        )

        return juz
    }

    protected fun juzByPage(page: Int) : ModelJuz? {
        return getIndex().find { it.page == page }?.let {
            ModelJuz(
                it.juz,
                it.id,
                it.surah,
                it.ayahInSurah
            )
        }
    }

    protected fun juzForAyah(ayah: Int) : Int {
        return getIndex().find { it.id == ayah }!!.juz
    }
}