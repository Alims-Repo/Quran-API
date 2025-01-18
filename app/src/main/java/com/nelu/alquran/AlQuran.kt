package com.nelu.alquran

import android.app.Application
import com.nelu.quran_api.di.QuranAPI

class AlQuran : Application() {

    override fun onCreate() {
        super.onCreate()
        QuranAPI.init(this, /* "XN2C-9V7L-KD5Q-YH8J" */ )
    }
}