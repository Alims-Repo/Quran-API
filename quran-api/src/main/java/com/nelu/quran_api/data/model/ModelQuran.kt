package com.nelu.quran_api.data.model

data class ModelQuran(
    val id: Int = 0,
    val surah: Int,
    val juz: Int,
    val page: Int,
    val ayahInSurah: Int,
    val arabic: String,
    val translation: List<String> = emptyList()
)