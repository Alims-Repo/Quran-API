package com.nelu.alquran

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.nelu.quran_data.di.QuranData
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class SplashActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

//        val pages = StringBuilder()
//
//        QuranData.page.getPageInfo().forEach {
//            pages.append("${it.page}|${it.start}|${it.end}\n")
//        }
//        val file = File(cacheDir, "pages")
//        file.writeText(pages.toString())


        CoroutineScope(Dispatchers.Main).launch {
            delay(1000L)
            val start = System.currentTimeMillis()
            Log.e(
                "DATA",
                QuranData.page.getPageInfoByAyah(listOf(1, 8, 647, 2344, 6003)).toString()
            )
            Log.e("Time Took", "${System.currentTimeMillis() - start} ms")
        }
    }
}