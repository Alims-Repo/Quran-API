package com.nelu.quran_api.data.db.dao

import com.nelu.quran_api.data.model.ModelTranslator

/**
 * # DaoTranslation
 *
 * Interface for accessing translation-related data in the Quran. This DAO provides methods
 * to retrieve lists of available translations, both from external and local sources.
 * Each method is designed to give structured access to translation metadata, allowing
 * efficient retrieval of translation information.
 */
interface DaoTranslation {

    /**
     * Retrieves a list of all available translations, including both online and local sources.
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
}