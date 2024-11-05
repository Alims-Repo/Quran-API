package com.nelu.quran_api.binary

import android.content.Context
import android.util.Log
import com.nelu.quran_api.data.constant.Sensitive.surahDataIndex
import com.nelu.quran_api.data.model.ModelSurah
import java.io.File
import java.io.FileInputStream
import java.nio.ByteBuffer
import java.nio.channels.Channels
import kotlin.collections.first

open class BinarySurah(context: Context) : BinaryIndex(context) {

    private val surahs = ArrayList<ModelSurah>()

    val surah = File(context.filesDir, "surahs.dat")

    protected fun surahByPage(page: Int) : List<ModelSurah> {
        return getIndexByPage(page).distinctBy { it.surah }.map { it.surah }.let { surah->
            if (surah.size == 1)
                 listOf(surahById(surah.first())!!)
            else surahList().filter {
                surah.contains(it.number)
            }
        }
    }

    protected fun surahByName(name: String) : List<ModelSurah> {
        return surahList().filter {
            it.englishName.contains(name, true)
            || it.arabicName.contains(name, true)
            || it.revelationType.contains(name, true)
            || it.englishTranslation.contains(name, true)
        }
    }

    protected fun surahByAyah(ayah: Int) : ModelSurah? {
        if (ayah > 6236 || ayah < 1) return null
        return surahById(getIndexByAyah(ayah)!!.surah)

    }

    protected fun surahList() : ArrayList<ModelSurah> {

        if (surahs.isNotEmpty()) return surahs

        val inputStream = FileInputStream(surah)
        val buffer = ByteBuffer.allocate(inputStream.channel.size().toInt())
        Channels.newChannel(inputStream).read(buffer)
        buffer.flip()

        var size = buffer.int

        repeat(size) {
            val number = buffer.int
            val startId = buffer.int

            // Read and allocate Arabic Name
            val arabicNameSize = buffer.int
            val arabicNameBytes = ByteArray(arabicNameSize)
            buffer.get(arabicNameBytes)

            // Read and allocate English Name
            val englishNameSize = buffer.int
            val englishNameBytes = ByteArray(englishNameSize)
            buffer.get(englishNameBytes)

            // Read and allocate English Translation
            val englishTranslationSize = buffer.int
            val englishTranslationBytes = ByteArray(englishTranslationSize)
            buffer.get(englishTranslationBytes)

            // Read and allocate Revelation Type
            val revelationTypeSize = buffer.int
            val revelationTypeBytes = ByteArray(revelationTypeSize)
            buffer.get(revelationTypeBytes)

            val numberOfAyahs = buffer.int


            surahs.add(
                ModelSurah(
                    number,
                    startId,
                    String(arabicNameBytes),
                    String(englishNameBytes),
                    String(englishTranslationBytes),
                    String(revelationTypeBytes),
                    numberOfAyahs
                )
            )
        }

        return surahs
    }

    protected fun surahById(id: Int) : ModelSurah? {
        val inputStream = FileInputStream(surah)
        val buffer = ByteBuffer.allocate(inputStream.channel.size().toInt())
        Channels.newChannel(inputStream).read(buffer)
        buffer.flip()

        var size = buffer.int
        val modelSurahList = mutableListOf<ModelSurah>()

        surahDataIndex.find {
            it.first.contains(id)
        }?.let {
            buffer.position(it.second)
            size = it.first.count()
        }

        repeat(size) {
            val number = buffer.int
            val startId = buffer.int

            // Read and allocate Arabic Name
            val arabicNameSize = buffer.int
            val arabicNameBytes = ByteArray(arabicNameSize)
            buffer.get(arabicNameBytes)

            // Read and allocate English Name
            val englishNameSize = buffer.int
            val englishNameBytes = ByteArray(englishNameSize)
            buffer.get(englishNameBytes)

            // Read and allocate English Translation
            val englishTranslationSize = buffer.int
            val englishTranslationBytes = ByteArray(englishTranslationSize)
            buffer.get(englishTranslationBytes)

            // Read and allocate Revelation Type
            val revelationTypeSize = buffer.int
            val revelationTypeBytes = ByteArray(revelationTypeSize)
            buffer.get(revelationTypeBytes)

            val numberOfAyahs = buffer.int

            if (number == id)
                return ModelSurah(
                    number,
                    startId,
                    String(arabicNameBytes),
                    String(englishNameBytes),
                    String(englishTranslationBytes),
                    String(revelationTypeBytes),
                    numberOfAyahs
                )
        }

        return null
    }
}