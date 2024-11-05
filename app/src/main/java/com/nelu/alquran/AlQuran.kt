package com.nelu.alquran

import android.app.Application
import com.nelu.quran_api.di.QuranAPI
import com.nelu.quran_api.utils.NativeUtils

class AlQuran : Application() {

    override fun onCreate() {
        super.onCreate()
        QuranAPI.init(this)
    }
}