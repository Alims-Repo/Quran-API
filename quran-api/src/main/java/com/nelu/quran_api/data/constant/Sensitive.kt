package com.nelu.quran_api.data.constant

/**
 * # Sensitive
 *
 * This object contains sensitive or essential constants used in the application.
 * It includes the `surahDataIndex`, which provides an index mapping of Surah ranges
 * to their respective positions in a data file.
 *
 * The `surahDataIndex` helps optimize data retrieval by allowing quick access to
 * specific Surah ranges, each associated with a starting byte position in a binary file.
 */
object Sensitive {

    /**
     * A list of Surah ranges mapped to their respective starting positions in a data file.
     *
     * Each entry in the list represents a range of Surahs (e.g., Surahs 1 to 19) associated with
     * a specific starting byte position in a binary file. This mapping facilitates efficient
     * navigation and retrieval of Surah data by providing the starting point for each Surah range.
     *
     * - `1..19 to 4`: Maps Surahs 1 to 19 to the byte position 4.
     * - `20..38 to 1640`: Maps Surahs 20 to 38 to the byte position 1640.
     * - `39..57 to 3259`: Maps Surahs 39 to 57 to the byte position 3259.
     * - `58..76 to 4941`: Maps Surahs 58 to 76 to the byte position 4941.
     * - `77..95 to 6693`: Maps Surahs 77 to 95 to the byte position 6693.
     * - `96..114 to 8426`: Maps Surahs 96 to 114 to the byte position 8426.
     */
    val surahDataIndex = listOf(
        1..19 to 4,
        20..38 to 1640,
        39..57 to 3259,
        58..76 to 4941,
        77..95 to 6693,
        96..114 to 8426,
    )
}