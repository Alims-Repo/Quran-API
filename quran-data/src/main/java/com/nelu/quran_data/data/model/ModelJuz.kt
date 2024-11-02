package com.nelu.quran_data.data.model

import org.json.JSONArray

data class ModelJuz(
    val number: Int,
    val startId: Int,
    val startSurah: Int,
    val totalAyah: Int
) {

    companion object {
        fun JSONArray.toJuzList() : List<ModelJuz> {
            val data = ArrayList<ModelJuz>()
            for (x in 0 until length()) {
                getJSONObject(x).run {
                    data.add(
                        ModelJuz(
                            number = getInt("number"),
                            startId = getInt("startId"),
                            startSurah = getInt("startSurah"),
                            totalAyah = getInt("totalAyah")
                        )
                    )
                }
            }
            return data
        }

        fun JSONArray.toJuz(number: Int) : ModelJuz {
            return getJSONObject(number-1).run {
                ModelJuz(
                    number = getInt("number"),
                    startId = getInt("startId"),
                    startSurah = getInt("startSurah"),
                    totalAyah = getInt("totalAyah")
                )
            }
        }
    }
}