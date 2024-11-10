package com.nelu.quran_api.binary

import android.content.Context
import com.nelu.quran_api.R
import com.nelu.quran_api.data.model.ModelTranslator
import java.nio.ByteBuffer
import java.nio.channels.Channels

/**
 * # BinaryTranslation
 *
 * This class handles the reading and processing of translation data from a binary resource file (`translator`).
 * It loads a list of [ModelTranslator] entries, each representing a translation with its associated language,
 * translator’s name, and code. The binary data is read directly from the app’s raw resources, parsed, and
 * converted into `ModelTranslator` objects.
 *
 * @param context The application context used to access resources.
 */
open class BinaryTranslation(context: Context) {

    private val resources = context.resources

    /**
     * Reads and returns a list of [ModelTranslator] objects from the binary translation file.
     *
     * The function reads binary data from `R.raw.translator`, where each entry in the file contains details
     * about a specific translation, such as the language, name, translator, and code. The data is parsed and
     * converted into `ModelTranslator` objects.
     *
     * @return A list of [ModelTranslator] entries, each representing a translation with metadata.
     */
    fun translationList(): List<ModelTranslator> {
        return resources.openRawResource(R.raw.translator).use { inputStream ->

            val buffer = ByteBuffer.allocate(inputStream.available())
            Channels.newChannel(inputStream).read(buffer)
            buffer.flip()

            // Total number of ModelTranslator entries in the file
            val size = buffer.int
            val modelTranslatorList = mutableListOf<ModelTranslator>()

            repeat(size) {
                // Read language name
                val languageSize = buffer.int
                val languageBytes = ByteArray(languageSize)
                buffer.get(languageBytes)
                val language = String(languageBytes, Charsets.UTF_8)

                // Read translation name
                val nameSize = buffer.int
                val nameBytes = ByteArray(nameSize)
                buffer.get(nameBytes)
                val name = String(nameBytes, Charsets.UTF_8)

                // Read translator's name
                val translatorSize = buffer.int
                val translatorBytes = ByteArray(translatorSize)
                buffer.get(translatorBytes)
                val translator = String(translatorBytes, Charsets.UTF_8)

                // Read translation code
                val codeSize = buffer.int
                val codeBytes = ByteArray(codeSize)
                buffer.get(codeBytes)
                val code = String(codeBytes, Charsets.UTF_8)

                modelTranslatorList.add(
                    ModelTranslator(
                        language = language,
                        name = name,
                        translator = translator,
                        code = code.replace(".", "_") // Replace periods with underscores for compatibility
                    )
                )
            }

            modelTranslatorList
        }
    }
}