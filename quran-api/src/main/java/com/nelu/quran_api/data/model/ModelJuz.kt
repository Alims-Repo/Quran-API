package com.nelu.quran_api.data.model

/**
 * # ModelJuz
 *
 * A data model representing a Juz (section) in the Quran. Each `ModelJuz` instance
 * provides details about a specific Juz, including its number, the starting Ayah ID,
 * the starting Surah, and the total number of Ayahs within that Juz.
 *
 * This structure is useful for applications needing efficient access to Juz-specific
 * data, such as navigating to a specific section of the Quran.
 *
 * @property number The Juz number (1 to 30), representing the specific section in the Quran.
 * @property startId The unique ID of the first Ayah in this Juz, used for precise navigation.
 * @property startSurah The Surah (chapter) number where this Juz begins.
 * @property totalAyah The total number of Ayahs (verses) contained within this Juz.
 */
data class ModelJuz(
    val number: Int,
    val startId: Int,
    val startSurah: Int,
    val totalAyah: Int
)