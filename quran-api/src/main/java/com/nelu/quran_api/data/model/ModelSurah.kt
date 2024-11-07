package com.nelu.quran_api.data.model

/**
 * # ModelSurah
 *
 * Represents a Surah (chapter) in the Quran. Each instance of `ModelSurah` provides information
 * about the chapter’s position, name in both Arabic and English, translation, revelation type, and
 * the total number of Ayahs (verses) within the Surah.
 *
 * This model is useful for applications that need to display or access detailed information about
 * each Surah in the Quran.
 *
 * @property number The Surah number (1 to 114), uniquely identifying the chapter.
 * @property startId The unique identifier of the first Ayah in this Surah, useful for navigating
 * between verses.
 * @property arabicName The Arabic name of the Surah (e.g., "الفاتحة" for Al-Fatihah).
 * @property englishName The English name of the Surah (e.g., "Al-Fatihah").
 * @property englishTranslation The English translation of the Surah name (e.g., "The Opening").
 * @property revelationType Indicates whether the Surah was revealed in Mecca or Medina.
 * @property numberOfAyahs The total number of Ayahs (verses) in the Surah.
 */
data class ModelSurah(
    val number: Int,
    val startId: Int,
    val arabicName: String,
    val englishName: String,
    val englishTranslation: String,
    val revelationType: String,
    val numberOfAyahs: Int
)