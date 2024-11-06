package com.nelu.quran_api.binary

import android.content.Context
import android.util.Log
import com.nelu.quran_api.data.model.ModelIndex
import com.nelu.quran_api.data.model.ModelQuran
import com.nelu.quran_api.utils.NativeUtils.readModelIndexListFromFileDescriptor
import com.nelu.quran_api.utils.NativeUtils.readQuranDataFromPath
import com.nelu.quran_api.utils.NativeUtils.readQuranDataFromPaths
import java.io.File
import java.io.FileInputStream

open class BinaryQuran(private val context: Context) : BinaryIndex(context) {

    private val quran = ArrayList<ModelQuran>(6236)

    protected fun quranList(translations: List<String>): List<ModelQuran> {
        if (quran.isNotEmpty())
            return quran

        val translationsData =  readQuranDataFromPaths(context, translations.map { "$it.dat" })

        val ayahTranslations = MutableList(translationsData.size) { "" }

        val arabicData = translationsData[0]

        val flatArray = getRawIndex()
        for (i in flatArray.indices step 5) {

            val idIndex = flatArray[i] - 1

            translationsData.forEachIndexed { i, translationArray ->
                ayahTranslations[i] = translationArray[idIndex]
            }

            quran.add(
                ModelQuran(
                    id = idIndex + 1,
                    surah = flatArray[i + 1],
                    juz = flatArray[i + 2],
                    page = flatArray[i + 3],
                    ayahInSurah = flatArray[i + 4],
                    arabic = arabicData[idIndex],
                    translation = ayahTranslations
                )
            )
        }

        return quran
    }
}