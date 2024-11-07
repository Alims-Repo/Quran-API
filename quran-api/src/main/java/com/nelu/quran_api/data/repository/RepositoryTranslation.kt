package com.nelu.quran_api.data.repository

import android.app.Application
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

/**
 * # RepositoryTranslation
 *
 * This class implements the [BaseTranslation] interface to manage Quranic translations. It
 * provides methods for retrieving available translations, accessing local translations, and
 * downloading new translations. The download method supports progress tracking and binary file storage.
 *
 * @param application The application context, used for file storage.
 * @param daoTranslation The Data Access Object (DAO) for managing translation data.
 */
class RepositoryTranslation(
    private val application: Application,
    private val daoTranslation: DaoTranslation
) : BaseTranslation {

    /**
     * Retrieves a list of all available translations.
     *
     * @return A list of [ModelTranslator] objects representing each available translation.
     */
    override fun getTranslationList(): List<ModelTranslator> {
        return daoTranslation.getTranslationList()
    }

    /**
     * Retrieves a list of translations that are available locally on the device.
     *
     * @return A list of [ModelTranslator] objects representing each locally stored translation.
     */
    override fun getLocalTranslationList(): List<ModelTranslator> {
        return daoTranslation.getLocalTranslationList()
    }

    /**
     * Downloads a specific translation identified by its unique code.
     *
     * This function initiates the download of a translation file and provides updates through the
     * [TranslationDownloadListener]. The listener reports download success, failure, and progress.
     *
     * @param code The unique code for the translation to download.
     * @param listener An instance of [TranslationDownloadListener] to handle download progress and status.
     */
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
                        Exception("Failed to download translation, code ${connection.responseCode}")
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
                    writeStringListToBinary("${application.filesDir}/$code.dat", file.readLines())
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

    /**
     * Converts a list of strings to binary format and writes it to a specified file.
     *
     * @param filePath The path of the file to write the binary data.
     * @param stringList A list of strings to be stored in binary format.
     */
    private fun writeStringListToBinary(filePath: String, stringList: List<String>) {
        try {
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