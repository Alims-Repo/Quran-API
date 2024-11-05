package com.nelu.quran_api.data.model

import androidx.annotation.Keep

@Keep
data class ModelIndex(
    val id: Int,
    val surah: Int,
    val juz: Int,
    val page: Int,
    val ayahInSurah: Int
)