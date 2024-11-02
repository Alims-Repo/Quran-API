package com.nelu.quran_data.data.model

data class ModelQuranData(
    val id: Int,
    val surah: Int,
    val ayah: Int,
    val arabic: String,
    val transliteration: String,
    val translation: List<ModelTranslation>
)