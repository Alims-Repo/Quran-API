package com.nelu.alquran

import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.nelu.quran_api.data.model.ModelSurah
import com.nelu.quran_api.di.QuranAPI
import com.nelu.quran_api.di.QuranAPI.loadData
import com.nelu.quran_api.di.QuranAPI.saveData
import com.nelu.quran_api.di.star
import com.nelu.quran_data.di.QuranData
import com.nelu.quran_data.utils.parser.QuranInfoParser.readQuran
import org.json.JSONArray
import java.io.File
import java.io.FileOutputStream
import java.io.InputStream
import java.nio.ByteBuffer
import kotlin.system.measureTimeMillis
import kotlin.time.measureTime
import java.io.DataInputStream
import java.io.FileInputStream
import java.io.IOException
import java.nio.MappedByteBuffer
import java.nio.channels.Channels
import java.nio.channels.FileChannel

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

        readStringListFromRawResource(com.nelu.quran_api.R.raw.arabic)?.let {
//            Translations.DaoTranslation.newBuilder()
           saveData(it)
        }

        findViewById<Button>(R.id.q_all).setOnClickListener {
            measureTime {
                loadData().let {
                    Log.e("PRINT", it.toString())
                }
//                readStringListFromRawResource(com.nelu.quran_api.R.raw.arabic)
//                readStringListFromBinary("${cacheDir}/arabic.dat")
//                QuranData.quran.getQuranDataAll()
            }.let { time->
                findViewById<TextView>(R.id.time).text = "$time"
            }
        }

        findViewById<Button>(R.id.q_surah).setOnClickListener {
            measureTime {
                readModelSurahListFromBinaryMapped(com.nelu.quran_api.R.raw.surahs)
//                readModelSurahListFromBinaryMapped("${cacheDir}/surahs.dat")
//                QuranData.quran.getQuranDataSurah(2)
            }.let { time->
                findViewById<TextView>(R.id.time).text = "$time"
            }
        }

//        star()

//        measureTime {
//            readStringListFromRawResource(com.nelu.quran_api.R.raw.arabic)
//                Log.e("PRINT", it.toString())
//            }
//            repeat(10) {
//                readQuran()
//                readStringListFromBinary("${cacheDir}/arabic.dat")
//            }
//            readQuran()

//            QuranData.surah.getSurahInfo()
//            readModelSurahListFromBinaryMapped("${cacheDir}/surahs.dat")
//            readModelSurahListFromBinaryFast("${cacheDir}/surahs.dat").let {
//                Log.e("Surahs", it.size.toString())
//            }
//        }.let { time->
//            findViewById<TextView>(R.id.time).text = "$time"
//        }
    }

    fun readStringListFromRawResource(resourceId: Int): List<String>? {
        return resources.openRawResource(resourceId).use { inputStream ->
            // Read the input stream into a ByteBuffer
            val buffer = ByteBuffer.allocate(inputStream.available())
            Channels.newChannel(inputStream).read(buffer)
            buffer.flip() // Prepare buffer for reading

            val size = buffer.int // Read the list size
            val stringList = mutableListOf<String>()

            repeat(size) {
                val stringSize = buffer.int // Read each string's byte length
                val stringBytes = ByteArray(stringSize)
                buffer.get(stringBytes) // Read the binary content of the string
                stringList.add(String(stringBytes, Charsets.UTF_8)) // Decode bytes to string
            }
            stringList
        }
    }

    fun readModelSurahListFromBinaryMapped(resourceId: Int): List<ModelSurah> {
        return resources.openRawResource(resourceId).use { inputStream ->
            // Allocate a ByteBuffer based on the size of the input stream
            val buffer = ByteBuffer.allocate(inputStream.available())
            Channels.newChannel(inputStream).read(buffer)
            buffer.flip() // Prepare buffer for reading

            val size = buffer.int // Read the list size
            val modelSurahList = mutableListOf<ModelSurah>()

            repeat(size) {
                val number = buffer.int
                val startId = buffer.int

                // Read Arabic Name
                val arabicNameSize = buffer.int
                val arabicNameBytes = ByteArray(arabicNameSize)
                buffer.get(arabicNameBytes)
                val arabicName = String(arabicNameBytes)

                // Read English Name
                val englishNameSize = buffer.int
                val englishNameBytes = ByteArray(englishNameSize)
                buffer.get(englishNameBytes)
                val englishName = String(englishNameBytes)

                // Read English Translation
                val englishTranslationSize = buffer.int
                val englishTranslationBytes = ByteArray(englishTranslationSize)
                buffer.get(englishTranslationBytes)
                val englishTranslation = String(englishTranslationBytes)

                // Read Revelation Type
                val revelationTypeSize = buffer.int
                val revelationTypeBytes = ByteArray(revelationTypeSize)
                buffer.get(revelationTypeBytes)
                val revelationType = String(revelationTypeBytes)

                val numberOfAyahs = buffer.int

                modelSurahList.add(
                    ModelSurah(
                        number,
                        startId,
                        arabicName,
                        englishName,
                        englishTranslation,
                        revelationType,
                        numberOfAyahs
                    )
                )
            }

            modelSurahList
        }
    }
}