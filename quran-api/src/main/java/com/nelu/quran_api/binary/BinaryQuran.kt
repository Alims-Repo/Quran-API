package com.nelu.quran_api.binary

import android.content.Context
import com.nelu.quran_api.data.model.ModelQuran
import com.nelu.quran_api.utils.NativeUtils.readQuranDataFromPaths

open class BinaryQuran(private val context: Context) : BinaryIndex(context) {

    private val quran = ArrayList<ModelQuran>(6236)

    fun quranList(translation: List<String>): List<ModelQuran> {
        if (quran.isNotEmpty())
            return quran

        val translations = ArrayList<String>().also {
            it.add("arabic.dat")
            it.addAll(translation.map { "$it.dat" })
        }

        val bundledData =  readQuranDataFromPaths(
            context, translations
        )

        val translationsData = bundledData.sliceArray(1 until bundledData.size)

        val arabicData = bundledData[0]

        val flatArray = getRawIndex()

        for (i in flatArray.indices step 5) {

            val idIndex = flatArray[i] - 1

            quran.add(
                ModelQuran(
                    id = idIndex + 1,
                    surah = flatArray[i + 1],
                    juz = flatArray[i + 2],
                    page = flatArray[i + 3],
                    ayahInSurah = flatArray[i + 4],
                    arabic = arabicData[idIndex],
                    translation = translationsData.map { it[idIndex] },
                )
            )
        }

        return quran
    }
}