package com.nelu.quran_data.data.model

data class ModelQuranData(
    val id: Int,
    val juz: Int,
    val page: Int,
    val ayah: Int,
    val surah: Int,
    val arabic: String,
    val transliteration: String,
    val translation: List<ModelTranslation>
)