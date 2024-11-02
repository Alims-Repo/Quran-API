package com.nelu.quran_data.data.constant

import com.nelu.quran_data.R
import com.nelu.quran_data.di.QuranData.context
import org.json.JSONArray

object Page {

    val json = JSONArray(
        context.resources.openRawResource(
            R.raw.pages
        ).bufferedReader().readText()
    )
}