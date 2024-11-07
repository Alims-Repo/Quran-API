package com.nelu.quran_api.data.model

import androidx.annotation.Keep

/**
 * # ModelIndex
 *
 * A data model representing an index entry in the Quran, with details about the Surah (chapter),
 * Juz (section), page, and the specific Ayah (verse) within a Surah. Each instance of `ModelIndex`
 * contains unique information about a single verse location in the Quran.
 *
 * This data structure is commonly used for navigating Quranic content efficiently in the application.
 *
 * @property id A unique identifier for the index entry, often used to distinguish between entries.
 * @property surah The Surah (chapter) number in which the Ayah is located.
 * @property juz The Juz (section) number that contains the Ayah, facilitating navigation by sections.
 * @property page The page number in the Quran where the Ayah appears.
 * @property ayahInSurah The specific Ayah number within the Surah.
 */
@Keep
data class ModelIndex(
    val id: Int,
    val surah: Int,
    val juz: Int,
    val page: Int,
    val ayahInSurah: Int
)