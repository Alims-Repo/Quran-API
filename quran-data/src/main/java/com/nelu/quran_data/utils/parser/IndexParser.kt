package com.nelu.quran_data.utils.parser

import com.nelu.quran_data.R
import com.nelu.quran_data.data.constant.ASCII.BREAK
import com.nelu.quran_data.data.constant.ASCII.DIVIDER
import com.nelu.quran_data.di.QuranData.context

object IndexParser {

//    private val indexes = mutableListOf<ModelIndex>()
//
//    fun readInfo(): List<ModelIndex> {
//
//        if (indexes.isNotEmpty()) return indexes
//
//        context.resources.openRawResource(R.raw.indexes).use { inputStream ->
//            val buffer = ByteArray(16384)
//            var bytesRead: Int
//
//            var currentValue = 0
//            var delimiterCount = 0
//
//            var id = 0
//            var surah = 0
//            var juz = 0
//            var page = 0
//            var ayahInSurah = 0
//
//            while (inputStream.read(buffer).also { bytesRead = it } != -1) {
//                for (i in 0 until bytesRead) {
//                    val byte = buffer[i]
//
//                    when (byte.toInt()) {
//                        DIVIDER -> {
//                            when (delimiterCount) {
//                                0 -> id = currentValue
//                                1 -> surah = currentValue
//                                2 -> juz = currentValue
//                                3 -> page = currentValue
//                            }
//                            currentValue = 0
//                            delimiterCount++
//                        }
//
//                        BREAK -> {
//                            ayahInSurah = currentValue
//                            indexes.add(ModelIndex(id, surah, juz, page, ayahInSurah))
//                            currentValue = 0
//                            delimiterCount = 0
//                        }
//
//                        else -> currentValue = currentValue * 10 + (byte - '0'.code.toByte())
//                    }
//                }
//            }
//        }
//
//        return indexes
//    }

}