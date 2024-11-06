package com.nelu.quran_api.binary

import com.nelu.quran_api.R
import android.content.Context
import com.nelu.quran_api.data.model.ModelTranslator
import java.nio.ByteBuffer
import java.nio.channels.Channels

open class BinaryTranslation(context: Context) {

    private val resources = context.resources

    fun translationList(): List<ModelTranslator> {
        return resources.openRawResource(R.raw.translator).use { inputStream ->

            val buffer = ByteBuffer.allocate(inputStream.available())
            Channels.newChannel(inputStream).read(buffer)
            buffer.flip()

            val size = buffer.int // Total number of ModelTranslator entries in the file
            val modelTranslatorList = mutableListOf<ModelTranslator>()

            repeat(size) {
                // Read Language
                val languageSize = buffer.int
                val languageBytes = ByteArray(languageSize)
                buffer.get(languageBytes)
                val language = String(languageBytes, Charsets.UTF_8)

                // Read Name
                val nameSize = buffer.int
                val nameBytes = ByteArray(nameSize)
                buffer.get(nameBytes)
                val name = String(nameBytes, Charsets.UTF_8)

                // Read Translator
                val translatorSize = buffer.int
                val translatorBytes = ByteArray(translatorSize)
                buffer.get(translatorBytes)
                val translator = String(translatorBytes, Charsets.UTF_8)

                // Read Code
                val codeSize = buffer.int
                val codeBytes = ByteArray(codeSize)
                buffer.get(codeBytes)
                val code = String(codeBytes, Charsets.UTF_8)

                modelTranslatorList.add(
                    ModelTranslator(
                        language = language,
                        name = name,
                        translator = translator,
                        code = code.replace(".", "_")
                    )
                )
            }

            modelTranslatorList
        }
    }
}