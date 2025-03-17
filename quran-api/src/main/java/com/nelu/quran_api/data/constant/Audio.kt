package com.nelu.quran_api.data.constant

import com.nelu.quran_api.data.model.ModelQari

object Audio {

    var data = listOf(
        ModelQari("ar.abdurrahmaansudais", "Abdurrahmaan As-Sudais"),
        ModelQari("ar.ahmedajamy", "Ahmed ibn Ali al-Ajamy"),
        ModelQari("ar.alafasy", "Mishary Rashid Alafas"),
        ModelQari("ar.hanirifai", "Hani Rifai"),
        ModelQari("ar.mahermuaiqly", "Maher Al Muaiqly"),
        ModelQari("ar.saoodshuraym", "Saood bin Ibraaheem Ash-Shuraym"),
    )

    fun Int.audioUrl() : Pair<String, String> {
        val model = data[2].id //data.random().id
        return Pair(
            model, "https://cdn.islamic.network/quran/audio/64/$model/$this.mp3"
        )
    }

}