package com.nelu.quran_api.data.repository.base

import com.nelu.quran_api.data.model.ModelTranslator

/**
 * # BaseTranslation
 *
 * Interface for managing access to translation-related data in the Quran. This interface provides methods
 * to retrieve available translations, access locally stored translations, and download new translations.
 * It also defines a listener interface for handling download progress and status updates.
 */
interface BaseTranslation {

    /**
     * Retrieves a list of all available translations.
     *
     * @return A list of [ModelTranslator] objects representing each available translation.
     */
    fun getTranslationList(): List<ModelTranslator>

    /**
     * Retrieves a list of translations that are available locally on the device.
     *
     * @return A list of [ModelTranslator] objects representing each locally stored translation.
     */
    fun getLocalTranslationList(): List<ModelTranslator>

    /**
     * Downloads a specific translation identified by its unique code.
     *
     * This function initiates the download of a translation file and provides updates through the
     * [TranslationDownloadListener]. The listener reports download success, failure, and progress.
     *
     * @param code The unique code for the translation to download.
     * @param listener An instance of [TranslationDownloadListener] to handle download progress and status.
     */
    fun downloadTranslation(
        code: String,
        listener: TranslationDownloadListener
    )

    /**
     * Listener interface for handling translation download events, such as success, failure,
     * and download progress updates. It provides callbacks to manage the download process.
     */
    interface TranslationDownloadListener {

        /**
         * Called when the download is completed successfully.
         */
        fun onSuccess()

        /**
         * Called if the download fails.
         *
         * @param e The exception that caused the failure.
         */
        fun onFailure(e: Exception)

        /**
         * Reports the download or encoding progress.
         *
         * @param type The type of progress being reported (either DOWNLOAD or ENCODE).
         * @param progress The current progress as an integer percentage.
         */
        fun onProgress(type: Type, progress: Int)

        /**
         * Enum representing the type of progress being reported.
         */
        enum class Type {
            DOWNLOAD, ENCODE
        }
    }
}