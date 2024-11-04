package com.nelu.alquran

import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.nelu.quran_api.data.constant.Sensitive.surahDataIndex
import com.nelu.quran_api.data.model.ModelSurah
import com.nelu.quran_api.data.model.ModelTranslator
import com.nelu.quran_api.utils.NativeUtils
import java.io.File
import java.io.FileOutputStream
import java.io.IOException
import java.nio.ByteBuffer
import java.nio.channels.Channels
import kotlin.time.measureTime

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


//        val stringList = mutableListOf<String>()
//
//        resources.openRawResource(com.nelu.quran_api.R.raw.en).use { inputStream ->
//            BufferedReader(InputStreamReader(inputStream)).use { reader ->
//                reader.forEachLine { line ->
//                    if (stringList.size == 6236)
//                        return@forEachLine
//                    stringList.add(line)
//                }
//            }
//        }

//        Log.e("DATA", stringList.last())
//
//        writeStringListToBinary("${cacheDir}/english.dat", stringList)

//        val string = ArrayList<String>()
//
//        val doc: Document = Jsoup.parse(html)
//        for (row: Element in doc.select("table.transList tbody tr")) {
//            val columns = row.select("td")
//            val language = columns[0].text()
//            val name = columns[1].text()
//            val translator = columns[2].text()
//            val code = columns[3].select("a").attr("href").split("/").last()
//
//            string.add("${language}|${name}|${translator}|${code}\n")
//        }
//        writeStringListToBinary("${cacheDir}/translator.dat", string)
//
//        Log.e("JSON", string.toString())

        val cacheFile = File(cacheDir, "english.dat")

        // Check if the file already exists in the cache
        if (!cacheFile.exists()) {
            resources.openRawResource(com.nelu.quran_api.R.raw.english).use { inputStream ->
                FileOutputStream(cacheFile).use { outputStream ->
                    inputStream.copyTo(outputStream)
                }
            }
        }


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
                    NativeUtils.readRawResourceAsStringArray(
                        this, "english.dat"
                    )
//                    NativeUtils.readStringFromStream(
//                        resources.openRawResource(com.nelu.quran_api.R.raw.english)
//                    )
                }

            }.let { time->
                findViewById<TextView>(R.id.time).text = "${time/10}"
            }
        }

        findViewById<Button>(R.id.translators_jvm).setOnClickListener {
            measureTime {
                repeat(10) {
                    readModelTranslatorListFromBinaryMapped(com.nelu.quran_api.R.raw.translator)
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

    fun writeModelTranslatorListToBinaryFast(filePath: String, modelTranslatorList: List<ModelTranslator>) {
        try {
            // Estimate the buffer size based on the approximate size of each ModelTranslator entry
            val bufferSize = (4 + modelTranslatorList.sumOf { 16 + it.language.length + it.name.length + it.translator.length + it.code.length }) * 2
            val buffer = ByteBuffer.allocate(bufferSize)

            buffer.putInt(modelTranslatorList.size) // Write the list size

            for (modelTranslator in modelTranslatorList) {
                val languageBytes = modelTranslator.language.toByteArray(Charsets.UTF_8)
                val nameBytes = modelTranslator.name.toByteArray(Charsets.UTF_8)
                val translatorBytes = modelTranslator.translator.toByteArray(Charsets.UTF_8)
                val codeBytes = modelTranslator.code.toByteArray(Charsets.UTF_8)

                // Write each field's size and data
                buffer.putInt(languageBytes.size)
                buffer.put(languageBytes)

                buffer.putInt(nameBytes.size)
                buffer.put(nameBytes)

                buffer.putInt(translatorBytes.size)
                buffer.put(translatorBytes)

                buffer.putInt(codeBytes.size)
                buffer.put(codeBytes)
            }

            buffer.flip() // Prepare the buffer for writing

            // Write to file in one go
            FileOutputStream(filePath).channel.use { channel ->
                channel.write(buffer)
            }
        } catch (e: IOException) {
            e.printStackTrace()
        }
    }

    fun readTranslator(resourceId: Int): List<ModelTranslator> {
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

            stringList.map { line ->
                val parts = line.split("|")
                ModelTranslator(
                    language = parts[0],
                    name = parts[1],
                    translator = parts[2],
                    code = parts[3].trim() // Remove any trailing newline characters
                )
            }
        }
    }

    fun readModelTranslatorListFromBinaryMapped(resourceId: Int): List<ModelTranslator> {
        return resources.openRawResource(resourceId).use { inputStream ->

            val buffer = ByteBuffer.allocate(inputStream.available())
            Channels.newChannel(inputStream).read(buffer)
            buffer.flip()

            val size = buffer.int // Total number of ModelTranslator entries in the file
            val modelTranslatorList = mutableListOf<ModelTranslator>()

            repeat(size) {
                // Read Language
                val languageSize = buffer.int
                val languageBytes = ByteArray(languageSize)
                buffer.get(languageBytes)
                val language = String(languageBytes, Charsets.UTF_8)

                // Read Name
                val nameSize = buffer.int
                val nameBytes = ByteArray(nameSize)
                buffer.get(nameBytes)
                val name = String(nameBytes, Charsets.UTF_8)

                // Read Translator
                val translatorSize = buffer.int
                val translatorBytes = ByteArray(translatorSize)
                buffer.get(translatorBytes)
                val translator = String(translatorBytes, Charsets.UTF_8)

                // Read Code
                val codeSize = buffer.int
                val codeBytes = ByteArray(codeSize)
                buffer.get(codeBytes)
                val code = String(codeBytes, Charsets.UTF_8)

                modelTranslatorList.add(
                    ModelTranslator(
                        language = language,
                        name = name,
                        translator = translator,
                        code = code
                    )
                )
            }

            modelTranslatorList
        }
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