package com.nelu.quran_data.data.repo

import android.util.Log
import com.nelu.quran_data.R
import com.nelu.quran_data.data.model.ModelPage
import com.nelu.quran_data.data.repo.base.BasePage
import com.nelu.quran_data.di.QuranData.context
import org.json.JSONArray
import org.json.JSONObject
import java.io.BufferedReader
import java.util.Scanner

class RepositoryPage : BasePage {

    override fun getPageInfo(): List<ModelPage> {
        return readPageInfo()
    }

    override fun getPageInfo(pageNo: Int): ModelPage? {
        return readPageInfo(pageNo)
    }

    override fun getPageInfoByAyah(ayahId: Int): ModelPage? {
        return readPageInfo().find { it.start <= ayahId && it.end >= ayahId }
    }

    override fun getPageInfoByAyah(ayahIds: List<Int>): List<ModelPage> {
        val pageInfo = readPageInfo()

        return ArrayList<ModelPage>().apply {
            ayahIds.forEach { ayahId ->
                add(pageInfo.find { it.end >= ayahId }!!)
            }
        }

    }

    private fun readPageInfo(page: Int): ModelPage {
        context.resources.openRawResource(
            R.raw.pages
        ).bufferedReader().readText().let {
            return JSONArray(it).getJSONObject(page - 1).run {
                ModelPage(
                    getInt("page"),
                    getInt("start"),
                    getInt("end")
                )
            }
        }
    }

    fun readPageInfo(): List<ModelPage> {
        val start = System.currentTimeMillis()
        val modelPages = mutableListOf<ModelPage>()
        context.resources.openRawResource(R.raw.page).use { inputStream ->
            val buffer = ByteArray(1024) // Adjust buffer size as needed
            var byteRead: Int
            var currentValue = 0
            var delimiterCount = 0
            var page = 0
            var start = 0
            var end = 0

            while (inputStream.read(buffer).also { byteRead = it } != -1) {
                for (i in 0 until byteRead) {
                    val char = buffer[i].toInt().toChar()
                    when {
                        char == '|' -> {
                            when (delimiterCount) {
                                0 -> page = currentValue
                                1 -> start = currentValue
                            }
                            currentValue = 0
                            delimiterCount++
                        }

                        char == '\n' -> {
                            end = currentValue
                            modelPages.add(ModelPage(page, start, end))
                            currentValue = 0
                            delimiterCount = 0
                        }

                        else -> currentValue = currentValue * 10 + (char - '0')
                    }
                }
            }
        }

        Log.e("Read Time", "${System.currentTimeMillis() - start} ms")
        return modelPages

//        val start = System.currentTimeMillis()
//        return context.resources.openRawResource(
//            R.raw.page
//        ).bufferedReader().readText().run {
//            Log.e("Read Time", "${System.currentTimeMillis() - start} ms")
//            ArrayList<ModelPage>().also {
////                map { line->
////                    val parts = line.split("|").map { it.trim() }
////                    it.add(ModelPage(parts[0].toInt(), parts[1].toInt(), parts[2].toInt()))
////                }
//            }
//        }
    }

    // Use this function for JSONArray
    fun estimateSizeOfMap(map: Map<*, *>): Long {
        var size = 0L

        for ((key, value) in map) {
            size += 8 // Size for the key reference
            size += 2 * (key.toString().length) // Size for the key characters

            size += when (value) {
                is String -> 8 + 2 * value.length // Reference + characters
                is Int -> 4 // Size of an integer
                is Double -> 8 // Size of a double
                is Boolean -> 1 // Size of a boolean
                is Map<*, *> -> estimateSizeOfMap(value) // Recursive call for nested maps
                else -> 0 // For unsupported data types
            }.toLong()
        }

        return size
    }

    fun estimateJSONArraySize(jsonArray: JSONArray): Long {
        var size = 0L
        size += 16 // Overhead for the JSONArray object itself

        for (i in 0 until jsonArray.length()) {
            val item = jsonArray.get(i)
            size += when (item) {
                is JSONObject -> estimateJSONObjectSize(item) // Handle JSONObjects
                is LinkedHashMap<*, *> -> estimateSizeOfMap(item) // Handle LinkedHashMaps
                is String -> 8 + 2 * item.length // Reference + characters
                is Int -> 4 // Size of an integer
                is Double -> 8 // Size of a double
                is Boolean -> 1 // Size of a boolean
                else -> 0 // For unsupported data types
            }.toLong()
        }

        return size
    }

    // Estimate size for a JSONObject
    fun estimateJSONObjectSize(jsonObject: JSONObject): Long {
        var size = 0L

        for (key in jsonObject.keys()) {
            size += 8 // Size for the key reference
            size += 2 * key.length // Size for the key characters

            val value = jsonObject.get(key)
            size += when (value) {
                is String -> 8 + 2 * value.length // Reference + characters
                is Int -> 4 // Size of an integer
                is Double -> 8 // Size of a double
                is Boolean -> 1 // Size of a boolean
                else -> 0 // For unsupported or complex data types
            }
        }

        return size
    }
}