package com.nelu.quran_api.binary

import android.content.Context
import com.nelu.quran_api.data.model.ModelIndex
import com.nelu.quran_api.utils.NativeUtils.readModelIndexListFromFileDescriptor
import java.io.File
import java.io.FileInputStream

/**
 * # BinaryIndex
 *
 * This class manages the loading and processing of Quranic index data from a binary file (`indexes.dat`)
 * stored in the app’s internal storage. It converts raw binary data into a structured list of [ModelIndex] objects,
 * enabling efficient access to Quranic data attributes such as `surah`, `juz`, `page`, and `ayahInSurah`.
 *
 * **Lazy Loading**: The data is read only when needed, and once loaded, it is cached in memory to
 * minimize repeated file access and improve performance.
 *
 * #### Usage Example
 * ```
 * // Initialize BinaryIndex with application context
 * val binaryIndex = BinaryIndex(context)
 *
 * // Access the processed list of ModelIndex entries
 * val quranIndexList = binaryIndex.getIndex()
 *
 * // Use the list for Quranic data operations, e.g., retrieving specific verse data
 * quranIndexList.forEach { index ->
 *     println("Surah: ${index.surah}, Ayah: ${index.ayahInSurah}")
 * }
 * ```
 *
 * @param context The application context, used to locate the `indexes.dat` file in the app's internal storage.
 */
open class BinaryIndex(context: Context) {

    // Raw binary data array, where each entry set of five integers corresponds to a single index entry
    private lateinit var rawIndex: IntArray

    // List of processed ModelIndex objects, cached for quick repeated access
    private val cache = ArrayList<ModelIndex>()

    // File reference to the binary index data file (`indexes.dat`) stored in internal storage
    private val indexes = File(context.filesDir, "indexes.dat")

    /**
     * Retrieves a list of [ModelIndex] objects created from the binary index data.
     *
     * If the list has already been populated, it returns the cached list.
     * Otherwise, it converts the raw binary data in [rawIndex] into structured [ModelIndex] objects
     * by processing each set of five integers, representing properties like `id`, `surah`, `juz`,
     * `page`, and `ayahInSurah`.
     *
     * @return A list of [ModelIndex] entries containing Quranic index data.
     */
    protected fun getIndex(): List<ModelIndex> {
        // Populate cache if it's empty
        if (cache.isEmpty()) {
            for (i in getRawIndex().indices step 5) {
                cache.add(
                    ModelIndex(
                        id = rawIndex[i],
                        surah = rawIndex[i + 1],
                        juz = rawIndex[i + 2],
                        page = rawIndex[i + 3],
                        ayahInSurah = rawIndex[i + 4]
                    )
                )
            }
        }

        return cache
    }

    /**
     * Loads and returns the raw binary index data as an [IntArray].
     *
     * If [rawIndex] is not yet initialized, this method reads data from the `indexes.dat` file,
     * using [readModelIndexListFromFileDescriptor] to load the file's content as an integer array.
     * Each group of five integers in this array corresponds to an entry in the Quranic index.
     *
     * **Lazy Loading**: The file is read only once; subsequent calls return the cached array.
     *
     * @return An [IntArray] containing the raw binary data for the Quranic index.
     */
    protected fun getRawIndex(): IntArray {
        // Read raw data from the binary file only if not already initialized
        if (::rawIndex.isInitialized.not()) {
            rawIndex = readModelIndexListFromFileDescriptor(
                FileInputStream(indexes).fd, indexes.length()
            )
        }

        return rawIndex
    }
}