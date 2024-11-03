package com.nelu.quran_data

import android.app.Application
import android.util.Log
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.nelu.quran_data.di.QuranData
import org.junit.Test
import org.junit.runner.RunWith
import kotlin.system.measureTimeMillis

@RunWith(AndroidJUnit4::class)
class RepositoryTest {

    @Test
    fun useAppContext() {
        val appContext = ApplicationProvider.getApplicationContext<Application>()

        measureTimeMillis {
            QuranData.init(appContext)
            QuranData.quran.getQuranDataAll()
        }.let {
            Log.d("RepositoryTest", "Time: $it ms")
        }
    }
}