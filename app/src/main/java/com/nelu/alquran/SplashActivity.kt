package com.nelu.alquran

import android.os.Bundle
import android.util.Log
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.nelu.quran_data.di.QuranData
import com.nelu.quran_data.utils.parser.IndexParser
import com.nelu.quran_data.utils.parser.PageInfoParser
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import org.json.JSONArray
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

//        QuranData.quran.getQuranDataAll()


//        measureTimeMillis {
//            val indexes = StringBuilder()
//            QuranData.quran.getQuranDataAll().forEach {
//                indexes.append("${it.id}|${it.juz}|${it.page}|${it.ayah}|${it.surah}\n")
//            }
//            val file = File(cacheDir, "indexes")
//            file.writeText(indexes.toString())
//        }.let { time->
//            findViewById<TextView>(R.id.time).text = "$time ms"
//        }

//        measureTimeMillis {
//            resources.openRawResource(com.nelu.quran_data.R.raw.quran_page).bufferedReader(Charsets.UTF_8).readText().let {
//                val json = JSONArray(it)
//                val indexes = StringBuilder()
//                for (i in 0 until json.length()) {
//                    val obj = json.getJSONObject(i)
//                    indexes.append("${obj.getInt("Id")}|${obj.getInt("surah")}|${obj.getInt("juz")}|${obj.getInt("page")}|${obj.getInt("ayah_in_surah")}\n")
//                }
//                val file = File(cacheDir, "indexes")
//                file.writeText(indexes.toString())
//            }
//        }.let { time->
//            findViewById<TextView>(R.id.time).text = "$time ms"
//        }

        measureTimeMillis {
            QuranData.quran.getQuranDataAll()
//            IndexParser.readInfo()
        }.let { time->
            findViewById<TextView>(R.id.time).text = "$time ms"
        }
    }
}