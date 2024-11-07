package com.nelu.quran_api.binary

import android.content.Context
import com.nelu.quran_api.data.model.ModelJuz

/**
 * # BinaryJuz
 *
 * This class extends [BinaryIndex] to provide operations specifically related to Juz data.
 * It reads Quranic index data and groups it by Juz, creating a list of [ModelJuz] entries
 * that represent each Juz with its associated data, such as the starting Surah, start Ayah,
 * and the total number of Ayahs in the Juz.
 *
 * @param context The application context used to access the `indexes.dat` file in internal storage.
 */
open class BinaryJuz(context: Context) : BinaryIndex(context) {

    private val juz = ArrayList<ModelJuz>()

    /**
     * Retrieves the list of [ModelJuz] objects, each representing a Juz in the Quran.
     *
     * If the list is already populated, it returns the cached list. Otherwise, it groups
     * the index data by Juz and maps each group to a [ModelJuz] entry, setting values such as
     * the starting Surah, start Ayah, and total number of Ayahs in the Juz.
     *
     * @return An [ArrayList] of [ModelJuz] objects representing the Quranic Juz structure.
     */
    protected fun juzList(): ArrayList<ModelJuz> {
        if (juz.isNotEmpty())
            return juz

        getIndex().groupBy { it.juz }.mapTo(juz) { (juzNumber, indexList) ->
            val firstItem = indexList.first()
            ModelJuz(
                number = juzNumber,
                startId = firstItem.id,
                startSurah = firstItem.surah,
                totalAyah = indexList.size
            )
        }

        return juz
    }

    /**
     * Finds and returns the Juz number for a given page.
     *
     * This function searches through the index data to find the Juz that contains
     * the specified page.
     *
     * @param page The page number in the Quran for which the Juz is to be found.
     * @return The Juz number associated with the given page.
     * @throws NullPointerException if no matching Juz is found for the given page.
     */
    protected fun juzForPage(page: Int): Int {
        return getIndex().find { it.page == page }!!.juz
    }

    /**
     * Finds and returns the Juz number for a given Ayah.
     *
     * This function searches through the index data to find the Juz that contains
     * the specified Ayah ID.
     *
     * @param ayah The unique ID of the Ayah for which the Juz is to be found.
     * @return The Juz number associated with the specified Ayah.
     * @throws NullPointerException if no matching Juz is found for the given Ayah ID.
     */
    protected fun juzForAyah(ayah: Int): Int {
        return getIndex().find { it.id == ayah }!!.juz
    }
}