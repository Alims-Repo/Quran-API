package com.nelu.alquran

import android.app.Application
import com.nelu.quran_api.utils.NativeUtils
import com.nelu.quran_data.di.QuranData

class AlQuran : Application() {

    override fun onCreate() {
        super.onCreate()
        NativeUtils
        //QuranData.init(this)
    }
}