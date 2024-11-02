package com.nelu.quran_data.utils.parser

import com.nelu.quran_data.R
import com.nelu.quran_data.data.model.ModelPage
import com.nelu.quran_data.data.model.ModelSurah
import com.nelu.quran_data.di.QuranData.context

object SurahInfoParser {

    private val modelSurahs = mutableListOf<ModelSurah>()

    fun readSurahInfo(): List<ModelSurah> {
        if (modelSurahs.isNotEmpty())
            return modelSurahs

        context.resources.openRawResource(R.raw.surahs).reader(Charsets.UTF_8).use { reader ->
            val buffer = CharArray(16384)
            var currentValue = 0
            var delimiterCount = 0

            var number = 0
            var startId = 0
            val arabicName = StringBuilder()
            val englishName = StringBuilder()
            val englishTranslation = StringBuilder()
            val revelationType = StringBuilder()
            var numberOfAyahs = 0

            // Read the input stream in chunks
            while (reader.read(buffer).also { it } != -1) {
                for (char in buffer) {
                    when (char) {
                        '|' -> { // Delimiter
                            when (delimiterCount) {
                                0 -> number = currentValue // Store the surah number
                                1 -> startId = currentValue // Store the start ID
                                2 -> {} // For arabicName, clear currentValue after this
                                3 -> {} // For englishName
                                4 -> {} // For englishTranslation
                                5 -> {} // For revelationType
                            }
                            currentValue = 0
                            delimiterCount++
                        }

                        '\n' -> { // New line
                            numberOfAyahs = currentValue // Store the number of ayahs
                            modelSurahs.add(
                                ModelSurah(
                                    number, startId, arabicName.toString(), englishName.toString(),
                                    englishTranslation.toString(), revelationType.toString(), numberOfAyahs
                                )
                            )
                            // Reset for the next surah
                            currentValue = 0
                            delimiterCount = 0
                            arabicName.clear()
                            englishName.clear()
                            englishTranslation.clear()
                            revelationType.clear()
                        }

                        else -> {
                            // Populate each field based on the delimiter count
                            when (delimiterCount) {
                                0, 1, 6 -> currentValue = currentValue * 10 + (char - '0')
                                2 -> arabicName.append(char)
                                3 -> englishName.append(char)
                                4 -> englishTranslation.append(char)
                                5 -> revelationType.append(char)
                            }
                        }
                    }
                }
            }
        }

        return modelSurahs
    }


    fun readSurahInfo(pos: Int): ModelSurah? {
        context.resources.openRawResource(R.raw.surahs).reader(Charsets.UTF_8).use { reader ->
            val buffer = CharArray(16384)
            var currentValue = 0
            var delimiterCount = 0

            var number = 0
            var startId = 0
            val arabicName = StringBuilder()
            val englishName = StringBuilder()
            val englishTranslation = StringBuilder()
            val revelationType = StringBuilder()
            var numberOfAyahs = 0

            // Read the input stream in chunks
            while (reader.read(buffer).also { it } != -1) {
                for (char in buffer) {
                    when (char) {
                        '|' -> { // Delimiter
                            when (delimiterCount) {
                                0 -> number = currentValue // Store the surah number
                                1 -> startId = currentValue // Store the start ID
                                2 -> {} // For arabicName, clear currentValue after this
                                3 -> {} // For englishName
                                4 -> {} // For englishTranslation
                                5 -> {} // For revelationType
                            }
                            currentValue = 0
                            delimiterCount++
                        }

                        '\n' -> { // New line
                            numberOfAyahs = currentValue // Store the number of ayahs
                            if (number == pos)
                                return ModelSurah(
                                    number, startId, arabicName.toString(), englishName.toString(),
                                    englishTranslation.toString(), revelationType.toString(), numberOfAyahs
                                )
                            // Reset for the next surah
                            currentValue = 0
                            delimiterCount = 0
                            arabicName.clear()
                            englishName.clear()
                            englishTranslation.clear()
                            revelationType.clear()
                        }

                        else -> {
                            // Populate each field based on the delimiter count
                            when (delimiterCount) {
                                0, 1, 6 -> currentValue = currentValue * 10 + (char - '0')
                                2 -> arabicName.append(char)
                                3 -> englishName.append(char)
                                4 -> englishTranslation.append(char)
                                5 -> revelationType.append(char)
                            }
                        }
                    }
                }
            }
        }

        return null
    }
}