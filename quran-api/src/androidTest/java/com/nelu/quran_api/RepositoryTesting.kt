package com.nelu.quran_api

import android.app.Application
import android.util.Log
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import com.nelu.quran_api.data.repository.base.BaseTranslation
import com.nelu.quran_api.di.QuranAPI
import org.junit.Test

import org.junit.runner.RunWith

/**
 * Instrumented test, which will execute on an Android device.
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
@RunWith(AndroidJUnit4::class)
class RepositoryTesting {

    val appContext = InstrumentationRegistry.getInstrumentation().targetContext

    @Test
    fun download() {
        QuranAPI.init(appContext.applicationContext as Application)

    }

}