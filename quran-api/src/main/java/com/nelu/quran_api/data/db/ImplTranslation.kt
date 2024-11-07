package com.nelu.quran_api.data.db

import android.app.Application
import com.nelu.quran_api.binary.BinaryTranslation
import com.nelu.quran_api.data.db.dao.DaoTranslation
import com.nelu.quran_api.data.model.ModelTranslator

/**
 * # ImplTranslation
 *
 * This class implements the [DaoTranslation] interface by extending [BinaryTranslation] to provide access
 * to Quranic translations. It offers methods to retrieve lists of available translations, including both
 * translations stored locally on the device and those available generally.
 *
 * @param application The application context, used for accessing resources and file storage.
 */
class ImplTranslation(
    private val application: Application
) : BinaryTranslation(application), DaoTranslation {

    /**
     * Retrieves a list of all available translations, including both online and local sources.
     *
     * @return A list of [ModelTranslator] objects representing each available translation.
     */
    override fun getTranslationList(): List<ModelTranslator> {
        return translationList()
    }

    /**
     * Retrieves a list of translations that are available locally on the device.
     *
     * This function checks the application's files directory for translation files and
     * filters the list of all available translations to include only those found locally.
     *
     * @return A list of [ModelTranslator] objects representing each locally stored translation.
     */
    override fun getLocalTranslationList(): List<ModelTranslator> {
        return (application.filesDir.listFiles()?.map { it.name } ?: emptyList()).run {
            translationList().filter {
                contains(it.code + ".dat")
            }
        }
    }
}