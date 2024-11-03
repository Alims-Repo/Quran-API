package com.nelu.quran_api.data.model

data class ModelSurah(
    val number: Int,
    val startId: Int,
    val arabicName: String,
    val englishName: String,
    val englishTranslation: String,
    val revelationType: String,
    val numberOfAyahs: Int
)