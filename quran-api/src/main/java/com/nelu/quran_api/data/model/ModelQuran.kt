package com.nelu.quran_api.data.model

/**
 * # ModelQuran
 *
 * Represents an Ayah (verse) in the Quran, containing metadata and content in multiple languages.
 * Each instance of `ModelQuran` provides details about the Ayah’s position in the Quran (e.g., Surah, Juz, page),
 * the Arabic text of the Ayah, and a list of translations in various languages.
 *
 * This model is useful for displaying Quranic content in both its original and translated forms.
 *
 * @property id The unique identifier for the Ayah.
 * @property surah The Surah (chapter) number where this Ayah is located.
 * @property juz The Juz (section) number that contains this Ayah, useful for section-based navigation.
 * @property page The page number in the Quran where this Ayah appears, useful for traditional page navigation.
 * @property ayahInSurah The Ayah number within its Surah, indicating its sequential position.
 * @property arabic The original Arabic text of the Ayah.
 * @property translation A list of translations for the Ayah in various languages, defaulting to an empty list.
 */
data class ModelQuran(
    val id: Int = 0,
    val surah: Int,
    val juz: Int,
    val page: Int,
    val ayahInSurah: Int,
    val arabic: String,
    val translation: List<ModelTranslationText> = emptyList()
) {

    data class ModelTranslationText(
        val text: String,
        val code: String
    )
}