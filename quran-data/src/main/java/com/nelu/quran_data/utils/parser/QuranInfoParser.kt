package com.nelu.quran_data.utils.parser

import android.util.Log
import com.nelu.quran_data.R
import com.nelu.quran_data.data.model.ModelJuz
import com.nelu.quran_data.data.model.ModelPage
import com.nelu.quran_data.di.QuranData.context
import java.io.ByteArrayOutputStream
import java.io.InputStreamReader

object QuranInfoParser {

    private val versesList = mutableListOf<String>()

    fun readQuran(): List<String> {

        if (versesList.isNotEmpty()) return versesList

        val start = System.currentTimeMillis()
        context.resources.openRawResource(R.raw.quran).use { inputStream ->
            val buffer = ByteArray(65536)
            val byteArrayOutputStream = ByteArrayOutputStream()

            var bytesRead: Int
            while (inputStream.read(buffer).also { bytesRead = it } != -1) {
                byteArrayOutputStream.write(buffer, 0, bytesRead)
            }

            // Convert the accumulated bytes to a String
            val allText = byteArrayOutputStream.toString(Charsets.UTF_8.name())


            // Split the content into lines and add to versesList directly
            versesList.addAll(allText.split("\n"))
//            Log.e("Time", "${System.currentTimeMillis() - start}")
        }

        return versesList
    }
    fun readQuran(range: IntRange): List<String> {
        readQuran()

        // Bound the indices within the available range
        val fromIndex = range.first.coerceAtLeast(0)
        val toIndex = range.last.coerceAtMost(versesList.size - 1)

        // Return the sublist within the specified range
        return if (fromIndex <= toIndex) versesList.subList(fromIndex, toIndex + 1) else emptyList()
    }

}