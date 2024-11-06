package com.nelu.quran_api.binary

import android.content.Context
import android.util.Log
import com.nelu.quran_api.data.model.ModelJuz
import com.nelu.quran_api.data.model.ModelQuran
import com.nelu.quran_api.data.model.ModelSurah
import com.nelu.quran_api.utils.NativeUtils.readRawResourceAsStringArray
import java.io.File
import java.io.FileInputStream
import java.nio.ByteBuffer
import java.nio.channels.Channels

open class BinaryQuran(private val context: Context) : BinaryIndex(context) {

    private val quran = ArrayList<ModelQuran>()

    protected fun quranList(translations: List<String>): ArrayList<ModelQuran> {
//        if (quran.isNotEmpty()) return quran

        quran.clear()
        // Load all translation data only once
        val translationsData = translations.map { readRawResourceAsStringArray(context, it)!! }

        // Process each index in a single pass
        getIndex().forEach { index ->
            val ayahTranslations = translationsData.map { it[index.id - 1] }

            quran.add(
                ModelQuran(
                    id = index.id,
                    surah = index.surah,
                    juz = index.juz,
                    page = index.page,
                    ayahInSurah = index.ayahInSurah,
                    arabic = translationsData[0][index.id - 1], // Assuming Arabic text is the first translation
                    translation = ayahTranslations
                )
            )
        }

        return quran
    }
}