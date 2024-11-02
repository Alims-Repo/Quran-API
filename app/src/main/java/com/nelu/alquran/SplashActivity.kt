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
import java.io.File
import kotlin.system.measureTimeMillis

class SplashActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

//        val pages = StringBuilder()
//        QuranData.surah.getSurahInfo().forEach {
//            pages.append("${it.number}|${it.arabicName}|${it.englishName}|${it.englishTranslation}|${it.revelationType}|${it.numberOfAyahs}\n")
//        }
//        val file = File(cacheDir, "surah")
//        file.writeText(pages.toString())

        //    val number: Int,
        //    val startId: Int,
        //    val startSurah: Int,
        //    val totalAyah: Int
//        val pages = StringBuilder()
//        QuranData.juz.getJuzInfo().forEach {
//            pages.append("${it.number}|${it.startId}|${it.startSurah}|${it.totalAyah}\n")
//        }
//        val file = File(cacheDir, "paras")
//        file.writeText(pages.toString())

        measureTimeMillis {
            QuranData.surah.getSurahInfo().let {
                Log.e("DATA", it.toString())
            }
        }.let { time->
            findViewById<TextView>(R.id.time).text = "$time ms"
        }
    }
}