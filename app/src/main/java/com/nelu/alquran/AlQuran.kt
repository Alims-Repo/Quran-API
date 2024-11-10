package com.nelu.alquran

import android.app.Application
import com.nelu.quran_api.di.QuranAPI

class AlQuran : Application() {

    override fun onCreate() {
        super.onCreate()
        QuranAPI.init(this)
    }
}