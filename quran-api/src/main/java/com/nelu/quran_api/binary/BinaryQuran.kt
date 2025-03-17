package com.nelu.quran_api.binary

import android.content.Context
import android.util.Log
import com.nelu.quran_api.data.model.ModelQuran
import com.nelu.quran_api.utils.NativeUtils.readQuranDataFromPaths

/**
 * # BinaryQuran
 *
 * This class extends [BinaryIndex] to handle the loading and processing of Quranic data, including Arabic text and translations.
 * It constructs a list of [ModelQuran] objects, each representing an Ayah with its metadata (e.g., Surah, Juz, page) and content
 * (Arabic text and translations). The data is read from binary files stored in the app’s internal storage, making it efficient for
 * large datasets.
 *
 * **Lazy Loading**: The Quranic data is loaded only when accessed for the first time, then cached to optimize performance.
 *
 * @param context The application context, used to locate and read Quranic data files in the app's internal storage.
 */
open class BinaryQuran(private val context: Context) : BinaryIndex(context) {

    // Cache of ModelQuran objects, preallocated for 6236 Ayahs
    private val translations = ArrayList<String>()
    private val quran = ArrayList<ModelQuran>(6236)

    /**
     * Retrieves the list of [ModelQuran] objects, each representing an Ayah in the Quran.
     *
     * If the list is already populated, it returns the cached list. Otherwise, it loads data from
     * binary files, including Arabic text (`arabic.dat`) and specified translations. The data is combined
     * with metadata from the Quranic index to construct each [ModelQuran] entry.
     *
     * @param translation A list of translation file names (without the `.dat` extension) to load along with the Arabic text.
     * @return A list of [ModelQuran] objects containing both Arabic text and translations.
     */
    fun quranList(translation: List<String>): List<ModelQuran> {
        // Return cached data if already populated
        if (quran.isNotEmpty() && this.translations == translation) {
            Log.e("Cache", translation.toString())
            return quran
        }

        quran.clear()

        val local = context.filesDir.listFiles()?.map { it.name } ?: emptyList()
        // Prepare the list of files to load, starting with Arabic text, then adding translations
        val translationFiles = mutableListOf("arabic.dat") + translation.map { "$it.dat" }.filter {
            local.contains(it)
        }

        // Read Arabic and translation data from binary files
        val bundledData = readQuranDataFromPaths(context, translationFiles)

        // Ensure Arabic text is correctly assigned
        val arabicData = bundledData.firstOrNull() ?: return emptyList()

        // Extract translation data, ensuring indexes align with translation codes
        val translationsData = bundledData.drop(1) // Excluding Arabic text
        val flatArray = getRawIndex()


        // Ensure translations are mapped correctly
        val translationMap = translationFiles.drop(1).associate { fileName ->
            fileName.removeSuffix(".dat") to translationsData[translationFiles.indexOf(fileName) - 1]
        }

        // Populate `quran` list with ModelQuran objects, combining metadata and text data
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
                    translation = translationMap.map { (code, data) ->
                        ModelQuran.ModelTranslationText(
                            text = data[idIndex], // Ensure correct mapping
                            code = code
                        )
                    }
                )
            )
        }

        this.translations.clear()
        this.translations.addAll(translation)

        return quran
    }
}