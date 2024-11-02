package com.nelu.alquran

import android.os.Bundle
import android.util.Log
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.nelu.quran_data.di.QuranData
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlin.system.measureTimeMillis

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


        measureTimeMillis {
            QuranData.page.getPageInfo(600).let {
                Log.e("DATA", it.toString())
            }
        }.let { time->
            findViewById<TextView>(R.id.time).text = "$time ms"
        }
    }
}