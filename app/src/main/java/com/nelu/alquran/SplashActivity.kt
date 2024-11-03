package com.nelu.alquran

import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.nelu.quran_api.data.constant.Sensitive
import com.nelu.quran_api.data.constant.Sensitive.surahDataIndex
import com.nelu.quran_api.data.model.ModelSurah
import com.nelu.quran_api.di.writeStringListToBinary
import com.nelu.quran_api.utils.NativeUtils
import com.nelu.quran_api.utils.readBinaryDataFromResource
import com.nelu.quran_data.di.QuranData
import java.io.BufferedReader
import java.io.File
import java.io.InputStreamReader
import java.nio.ByteBuffer
import kotlin.time.measureTime
import java.nio.channels.Channels

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


        val stringList = mutableListOf<String>()

        resources.openRawResource(com.nelu.quran_api.R.raw.en).use { inputStream ->
            BufferedReader(InputStreamReader(inputStream)).use { reader ->
                reader.forEachLine { line ->
                    if (stringList.size == 6236)
                        return@forEachLine
                    stringList.add(line)
                }
            }
        }

        Log.e("DATA", stringList.last())
//
//        writeStringListToBinary("${cacheDir}/english.dat", stringList)

        findViewById<Button>(R.id.quran_jni).setOnClickListener {
            measureTime {
//                readStringListFromRawResource(com.nelu.quran_api.R.raw.arabic)
//                NativeUtils.readBinaryData("${cacheDir}/arabic.dat")
//                readStringListFromBinary("${cacheDir}/arabic.dat")
//                QuranData.quran.getQuranDataAll()
                //"${cacheDir}/arabic.dat"
//                repeat(10) {
//                    readBinaryDataFromResource(com.nelu.quran_api.R.raw.arabic)
//                }

                repeat(10) {
                    NativeUtils.readStringFromStream(
                        resources.openRawResource(com.nelu.quran_api.R.raw.english)
                    )
                }

            }.let { time->
                findViewById<TextView>(R.id.time).text = "${time/10}"
            }
        }

        findViewById<Button>(R.id.quran_jvm).setOnClickListener {
            measureTime {
                repeat(10) {
                    readStringListFromRawResource(com.nelu.quran_api.R.raw.english)
                }
//
//                readModelSurahListFromBinaryMapped(com.nelu.quran_api.R.raw.surahs, 22) //.let {
//                    Log.e("PRINT", it.map { it.number }.toString())
//                }
//                readModelSurahListFromBinaryMapped("${cacheDir}/surahs.dat")
//                QuranData.quran.getQuranDataSurah(2)
            }.let { time->
                findViewById<TextView>(R.id.time).text = "${time/10}"
            }
        }

        findViewById<Button>(R.id.surah_jvm).setOnClickListener {
            measureTime {
//                repeat(10) {
//                    readStringListFromRawResource(com.nelu.quran_api.R.raw.english)
//                }

                repeat(10) {
                    readModelSurahListFromBinaryMapped(com.nelu.quran_api.R.raw.surahs, 22)
                }
//
                 //.let {
//                    Log.e("PRINT", it.map { it.number }.toString())
//                }
//                readModelSurahListFromBinaryMapped("${cacheDir}/surahs.dat")
//                QuranData.quran.getQuranDataSurah(2)
            }.let { time->
                findViewById<TextView>(R.id.time).text = "${time/10}"
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
        var datas = ArrayList<Int>()
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
                datas.add(stringSize)
            }

//            datas.chunked(78).forEach {
//                Log.e("PRINT", it.sum().toString())
//            }

            stringList
        }
    }

    fun readModelSurahListFromBinaryMapped(resourceId: Int, id: Int? = null): List<ModelSurah> {
        return resources.openRawResource(resourceId).use { inputStream ->

            val buffer = ByteBuffer.allocate(inputStream.available())
            Channels.newChannel(inputStream).read(buffer)
            buffer.flip()

            var size = buffer.int
            val modelSurahList = mutableListOf<ModelSurah>()

            surahDataIndex.find {
                it.first.contains(id)
            }?.let {
                buffer.position(it.second)
                size = it.first.count()
            }

            repeat(size) {
                val number = buffer.int
                val startId = buffer.int

                // Read and allocate Arabic Name
                val arabicNameSize = buffer.int
                val arabicNameBytes = ByteArray(arabicNameSize)
                buffer.get(arabicNameBytes)

                // Read and allocate English Name
                val englishNameSize = buffer.int
                val englishNameBytes = ByteArray(englishNameSize)
                buffer.get(englishNameBytes)

                // Read and allocate English Translation
                val englishTranslationSize = buffer.int
                val englishTranslationBytes = ByteArray(englishTranslationSize)
                buffer.get(englishTranslationBytes)

                // Read and allocate Revelation Type
                val revelationTypeSize = buffer.int
                val revelationTypeBytes = ByteArray(revelationTypeSize)
                buffer.get(revelationTypeBytes)

                val numberOfAyahs = buffer.int

                modelSurahList.add(
                    ModelSurah(
                        number,
                        startId,
                        String(arabicNameBytes),
                        String(englishNameBytes),
                        String(englishTranslationBytes),
                        String(revelationTypeBytes),
                        numberOfAyahs
                    )
                )

                if (number == id)
                    return modelSurahList
            }

            modelSurahList
        }
    }
}