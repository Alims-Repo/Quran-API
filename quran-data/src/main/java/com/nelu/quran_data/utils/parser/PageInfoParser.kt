package com.nelu.quran_data.utils.parser

import com.nelu.quran_data.R
import com.nelu.quran_data.data.model.ModelPage
import com.nelu.quran_data.di.QuranData.context

object PageInfoParser {

    fun readPageInfo(): List<ModelPage> {

        val modelPages = mutableListOf<ModelPage>()

        context.resources.openRawResource(R.raw.pages).use { inputStream ->
            val buffer = ByteArray(16384)
            var bytesRead: Int

            var currentValue = 0
            var delimiterCount = 0

            var (page, start, end) = Triple(0, 0, 0)

            // Read the input stream in chunks
            while (inputStream.read(buffer).also { bytesRead = it } != -1) {
                for (i in 0 until bytesRead) {
                    val byte = buffer[i]

                    when (byte.toInt()) {
                        124 -> { // ASCII for '|'
                            when (delimiterCount) {
                                0 -> page = currentValue
                                1 -> start = currentValue
                            }
                            currentValue = 0
                            delimiterCount++
                        }

                        10 -> { // ASCII for '\n'
                            end = currentValue
                            modelPages.add(ModelPage(page, start, end))
                            currentValue = 0
                            delimiterCount = 0
                        }

                        else -> {
                            // Calculate current value directly from byte
                            currentValue = currentValue * 10 + (byte - '0'.code.toByte())
                        }
                    }
                }
            }
        }

        return modelPages
    }

    fun readPageInfo(pos: Int): ModelPage? {

        context.resources.openRawResource(R.raw.pages).use { inputStream ->
            val buffer = ByteArray(16384)
            var bytesRead: Int

            var currentValue = 0
            var delimiterCount = 0

            var (page, start, end) = Triple(0, 0, 0)

            // Read the input stream in chunks
            while (inputStream.read(buffer).also { bytesRead = it } != -1) {
                for (i in 0 until bytesRead) {

                    val byte = buffer[i]

                    when (byte.toInt()) {
                        124 -> { // ASCII for '|'
                            when (delimiterCount) {
                                0 -> page = currentValue
                                1 -> start = currentValue
                            }
                            currentValue = 0
                            delimiterCount++
                        }

                        10 -> { // ASCII for '\n'
                            end = currentValue
                            if (page == pos)
                                return ModelPage(page, start, end)
                            currentValue = 0
                            delimiterCount = 0
                        }

                        else -> {
                            // Calculate current value directly from byte
                            currentValue = currentValue * 10 + (byte - '0'.code.toByte())
                        }
                    }
                }
            }
        }
        return null
    }
}