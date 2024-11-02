package com.nelu.quran_data.data.model

import org.json.JSONArray

data class ModelSurah(
    val number: Int,
    val startId: Int,
    val arabicName: String,
    val englishName: String,
    val englishTranslation: String,
    val revelationType: String,
    val numberOfAyahs: Int
) {

    companion object {
//        fun JSONArray.toSurahList() : List<ModelSurah> {
//            val data = ArrayList<ModelSurah>()
//            for (x in 0 until length()) {
//                getJSONObject(x).run {
//                    data.add(
//                        ModelSurah(
//                            number = getInt("number"),
//                            arabicName = getString("arabicName"),
//                            englishName = getString("englishName"),
//                            englishTranslation = getString("englishTranslation"),
//                            revelationType = getString("revelationType"),
//                            numberOfAyahs = getInt("numberOfAyahs")
//                        )
//                    )
//                }
//            }
//            return data
//        }

        fun JSONArray.getSurah(number: Int) : ModelSurah {
            return getJSONObject(number-1).run {
                ModelSurah(
                    number = getInt("number"),
                    startId = 0,
                    arabicName = getString("arabicName"),
                    englishName = getString("englishName"),
                    englishTranslation = getString("englishTranslation"),
                    revelationType = getString("revelationType"),
                    numberOfAyahs = getInt("numberOfAyahs")
                )
            }
        }
    }
}