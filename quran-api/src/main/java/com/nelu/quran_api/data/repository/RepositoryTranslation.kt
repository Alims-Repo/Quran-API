package com.nelu.quran_api.data.repository

import android.app.Application
import android.util.Log
import com.nelu.quran_api.data.db.dao.DaoSurah
import com.nelu.quran_api.data.db.dao.DaoTranslation
import com.nelu.quran_api.data.model.ModelTranslator
import com.nelu.quran_api.data.repository.base.BaseTranslation
import com.nelu.quran_api.data.repository.base.BaseTranslation.TranslationDownloadListener.Type
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.io.File
import java.io.FileOutputStream
import java.io.IOException
import java.io.InputStream
import java.net.HttpURLConnection
import java.net.URL
import java.nio.ByteBuffer

class RepositoryTranslation(
    private val application: Application,
    private val daoTranslation: DaoTranslation
) : BaseTranslation {

    override fun getTranslationList(): List<ModelTranslator> {
        return daoTranslation.getTranslationList()
    }

    override fun getLocalTranslationList(): List<ModelTranslator> {
        return daoTranslation.getLocalTranslationList()
    }

    override fun downloadTranslation(
        code: String,
        listener: BaseTranslation.TranslationDownloadListener
    ) {
        CoroutineScope(Dispatchers.IO).launch {
            val url = URL("https://tanzil.net/trans/${code.replace("_", ".")}&type=txt")
            var connection: HttpURLConnection? = null
            var inputStream: InputStream? = null
            var outputStream: FileOutputStream? = null

            try {
                connection = url.openConnection() as HttpURLConnection
                connection.requestMethod = "GET"
                connection.connectTimeout = 10000
                connection.readTimeout = 10000
                connection.connect()

                if (connection.responseCode != HttpURLConnection.HTTP_OK) {
                    listener.onFailure(
                        Exception(
                            "Failed to download translation, code ${connection.responseCode}"
                        )
                    )
                    return@launch
                }

                inputStream = connection.inputStream
                val file = File(application.filesDir, "$code.txt")
                outputStream = FileOutputStream(file)

                val buffer = ByteArray(4096)
                var bytesRead: Int
                var totalBytesRead = 0.0

                val estimatedTotalSize = 2.0 * 1024 * 1024
                var lastProgress = 0

                while (inputStream.read(buffer).also { bytesRead = it } != -1) {
                    outputStream.write(buffer, 0, bytesRead)
                    totalBytesRead += bytesRead
                    val progress = ((totalBytesRead / estimatedTotalSize) * 100).toInt()
                    if (progress != lastProgress) {
                        listener.onProgress(Type.DOWNLOAD, progress)
                        lastProgress = progress
                    }
                }

                try {
                    writeStringListToBinary(
                        "${application.filesDir}/$code.dat", file.readLines()
                    )
                    file.deleteRecursively()
                    listener.onSuccess()
                } catch (e: Exception) {
                    listener.onFailure(e)
                    return@launch
                }

                outputStream.flush()
            } catch (e: Exception) {
                listener.onFailure(e)
            } finally {
                inputStream?.close()
                outputStream?.close()
                connection?.disconnect()
            }
        }
    }

    private fun writeStringListToBinary(filePath: String, stringList: List<String>) {
        try {
            // Calculate total buffer size: 4 bytes for list size, plus 4 bytes for each string length and the string content itself
            val bufferSize = 4 + stringList.sumOf { 4 + it.toByteArray().size }
            val buffer = ByteBuffer.allocate(bufferSize)

            buffer.putInt(stringList.size) // Write the total number of strings

            for (str in stringList) {
                val strBytes = str.toByteArray(Charsets.UTF_8)
                buffer.putInt(strBytes.size) // Write each string's byte length
                buffer.put(strBytes)         // Write the actual string content in binary form
            }

            buffer.flip() // Prepare buffer for writing

            // Write the buffer to file in one go
            FileOutputStream(filePath).channel.use { channel ->
                channel.write(buffer)
            }
        } catch (e: IOException) {
            e.printStackTrace()
        }
    }
}