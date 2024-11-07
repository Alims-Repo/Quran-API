package com.nelu.quran_api.binary

import android.content.Context
import com.nelu.quran_api.data.constant.Sensitive.surahDataIndex
import com.nelu.quran_api.data.model.ModelSurah
import java.io.File
import java.io.FileInputStream
import java.nio.ByteBuffer
import java.nio.channels.Channels

/**
 * # BinarySurah
 *
 * This class extends [BinaryIndex] to handle the reading and processing of Surah data from a binary file (`surahs.dat`).
 * It provides methods for retrieving a complete list of Surah details or fetching a specific Surah by its ID.
 * The binary data is read only when necessary, and once loaded, it is cached in memory to optimize performance.
 *
 * @param context The application context, used to access the `surahs.dat` file in internal storage.
 */
open class BinarySurah(context: Context) : BinaryIndex(context) {

    private val surahs = ArrayList<ModelSurah>()

    private val surah = File(context.filesDir, "surahs.dat")

    /**
     * Retrieves a list of [ModelSurah] objects containing detailed information about each Surah.
     *
     * If the list is already populated, it returns the cached list. Otherwise, it reads the data from
     * the binary file (`surahs.dat`), extracting fields such as the Surah number, starting Ayah ID,
     * Arabic name, English name, English translation, revelation type, and the number of Ayahs.
     *
     * @return An [ArrayList] of [ModelSurah] objects representing each Surah in the Quran.
     */
    protected fun surahList(): ArrayList<ModelSurah> {
        if (surahs.isNotEmpty())
            return surahs

        val inputStream = FileInputStream(surah)
        val buffer = ByteBuffer.allocate(inputStream.channel.size().toInt())
        Channels.newChannel(inputStream).read(buffer)
        buffer.flip()

        val size = buffer.int

        repeat(size) {
            val number = buffer.int
            val startId = buffer.int

            // Read Arabic Name
            val arabicNameSize = buffer.int
            val arabicNameBytes = ByteArray(arabicNameSize)
            buffer.get(arabicNameBytes)

            // Read English Name
            val englishNameSize = buffer.int
            val englishNameBytes = ByteArray(englishNameSize)
            buffer.get(englishNameBytes)

            // Read English Translation
            val englishTranslationSize = buffer.int
            val englishTranslationBytes = ByteArray(englishTranslationSize)
            buffer.get(englishTranslationBytes)

            // Read Revelation Type
            val revelationTypeSize = buffer.int
            val revelationTypeBytes = ByteArray(revelationTypeSize)
            buffer.get(revelationTypeBytes)

            val numberOfAyahs = buffer.int

            surahs.add(
                ModelSurah(
                    number,
                    startId,
                    String(arabicNameBytes),
                    String(englishNameBytes),
                    String(englishTranslationBytes),
                    String(revelationTypeBytes),
                    numberOfAyahs
                )
            )
        }

        return surahs
    }

    /**
     * Retrieves a specific [ModelSurah] object by its Surah ID.
     *
     * If the Surah data is already loaded, it searches the cached list. Otherwise, it reads from the binary file,
     * leveraging the [surahDataIndex] to quickly locate the specific Surah's data position in the file.
     * If the Surah is found, the function returns a populated [ModelSurah] object; otherwise, it returns `null`.
     *
     * @param id The Surah number (ID) to retrieve.
     * @return A [ModelSurah] object with the specified Surah ID, or `null` if not found.
     */
    protected fun surahById(id: Int): ModelSurah? {
        if (surahs.isNotEmpty())
            return surahs.find { it.number == id }

        val inputStream = FileInputStream(surah)
        val buffer = ByteBuffer.allocate(inputStream.channel.size().toInt())
        Channels.newChannel(inputStream).read(buffer)
        buffer.flip()

        var size = buffer.int

        // Use surahDataIndex to find the position in the file
        surahDataIndex.find { it.first.contains(id) }?.let {
            buffer.position(it.second)
            size = it.first.count()
        }

        repeat(size) {
            val number = buffer.int
            val startId = buffer.int

            // Read Arabic Name
            val arabicNameSize = buffer.int
            val arabicNameBytes = ByteArray(arabicNameSize)
            buffer.get(arabicNameBytes)

            // Read English Name
            val englishNameSize = buffer.int
            val englishNameBytes = ByteArray(englishNameSize)
            buffer.get(englishNameBytes)

            // Read English Translation
            val englishTranslationSize = buffer.int
            val englishTranslationBytes = ByteArray(englishTranslationSize)
            buffer.get(englishTranslationBytes)

            // Read Revelation Type
            val revelationTypeSize = buffer.int
            val revelationTypeBytes = ByteArray(revelationTypeSize)
            buffer.get(revelationTypeBytes)

            val numberOfAyahs = buffer.int

            if (number == id)
                return ModelSurah(
                    number,
                    startId,
                    String(arabicNameBytes),
                    String(englishNameBytes),
                    String(englishTranslationBytes),
                    String(revelationTypeBytes),
                    numberOfAyahs
                )
        }

        return null
    }
}