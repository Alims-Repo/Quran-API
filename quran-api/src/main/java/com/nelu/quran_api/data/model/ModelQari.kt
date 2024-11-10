package com.nelu.quran_api.data.model

import org.intellij.lang.annotations.Language

data class ModelQari(
    val id: String,
    val language: String,
    val name: String,
    val emglishName: String,
    val image: String
)

//"identifier": "ar.ahmedajamy",
//    "language": "ar",
//    "name": "أحمد بن علي العجمي",
//    "englishName": "Ahmed ibn Ali al-Ajamy",
//    "format": "audio",
//    "type": "Verse by verse",
//    "direction": null,
//    "image": "https://i1.sndcdn.com/artworks-000455100471-tvhist-t500x500.jpg"