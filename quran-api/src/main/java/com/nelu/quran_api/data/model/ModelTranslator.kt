package com.nelu.quran_api.data.model


/**
 * # ModelTranslator
 *
 * Represents metadata about a translation of the Quran. Each instance of `ModelTranslator`
 * provides information about a specific translation, including the language, translator’s name,
 * and a unique code used to identify the translation file.
 *
 * This model is useful for managing different translations and displaying relevant information
 * about each one in the application.
 *
 * @property code A unique code identifying the translation, typically used to locate the associated file.
 * @property name The name of the translation, often indicating its specific style or version.
 * @property language The language in which the translation is provided (e.g., "English", "French").
 * @property translator The name of the translator(s) who created this translation.
 */
data class ModelTranslator(
    val code: String,
    val name: String,
    val language: String,
    val translator: String
)