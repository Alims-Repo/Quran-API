package com.nelu.quran_api.di

import android.content.Context
import android.util.Log
import com.nelu.quran_data.data.model.ModelSurah
import com.nelu.quran_data.di.QuranData
import org.json.JSONArray
import java.io.DataOutputStream
import java.io.File
import java.io.FileOutputStream
import java.io.IOException
import java.io.OutputStream
import java.nio.ByteBuffer
import kotlin.system.measureTimeMillis

//fun Context.star(data: List<String>) {
//    writeStringListToBinary("${cacheDir}/arabic.dat", QuranData.quran.getQuranDataAll().map { it.arabic })
////    writeSurahsToBinary(QuranData.surah.getSurahInfo(), openFileOutput("surahs.dat", Context.MODE_PRIVATE))
////    QuranData.index.getAll()
//}

fun writeStringListToBinary(filePath: String, stringList: List<String>) {
    try {
        // Calculate total buffer size: 4 bytes for list size, plus 4 bytes for each string length and the string content itself
        val bufferSize = 4 + stringList.sumOf { 4 + it.toByteArray().size }
        val buffer = ByteBuffer.allocate(bufferSize)

        buffer.putInt(stringList.size) // Write the total number of strings

        for (str in stringList) {
            val strBytes = str.toByteArray(Charsets.UTF_8)
            buffer.putInt(strBytes.size) // Write each string's byte length
            buffer.put(strBytes)         // Write the actual string content in binary form
        }

        buffer.flip() // Prepare buffer for writing

        // Write the buffer to file in one go
        FileOutputStream(filePath).channel.use { channel ->
            channel.write(buffer)
        }
    } catch (e: IOException) {
        e.printStackTrace()
    }
}

//fun writeModelSurahListToBinaryFast(filePath: String, modelSurahList: List<ModelSurah>) {
//    try {
//        // Estimate the size for ByteBuffer
//        val bufferSize = (4 + modelSurahList.sumOf { 20 + it.arabicName.length + it.englishName.length + it.englishTranslation.length + it.revelationType.length }) * 2
//        val buffer = ByteBuffer.allocate(bufferSize)
//
//        buffer.putInt(modelSurahList.size) // Write list size
//        for (modelSurah in modelSurahList) {
//            buffer.putInt(modelSurah.number)
//            buffer.putInt(modelSurah.startId)
//            buffer.putInt(modelSurah.arabicName.toByteArray().size)
//            buffer.put(modelSurah.arabicName.toByteArray())
//            buffer.putInt(modelSurah.englishName.toByteArray().size)
//            buffer.put(modelSurah.englishName.toByteArray())
//            buffer.putInt(modelSurah.englishTranslation.toByteArray().size) // length of English translation
//            buffer.put(modelSurah.englishTranslation.toByteArray())
//            buffer.putInt(modelSurah.revelationType.toByteArray().size) // length of revelation type
//            buffer.put(modelSurah.revelationType.toByteArray())
//            buffer.putInt(modelSurah.numberOfAyahs)
//        }
//
//        buffer.flip() // Prepare the buffer for writing
//
//        // Write to file in one go
//        FileOutputStream(filePath).channel.use { channel ->
//            channel.write(buffer)
//        }
//    } catch (e: IOException) {
//        e.printStackTrace()
//    }
//}

