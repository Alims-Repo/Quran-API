package com.nelu.alquran

import androidx.test.platform.app.InstrumentationRegistry
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.nelu.quran_api.data.model.ModelQuran
import com.nelu.quran_api.utils.NativeUtils
import com.nelu.quran_api.utils.NativeUtils.readRawResourceAsModelIndexArray

import org.junit.Test
import org.junit.runner.RunWith

import org.junit.Assert.*


@RunWith(AndroidJUnit4::class)
class BenchmarkTest {

    private val context = InstrumentationRegistry.getInstrumentation().targetContext

    @Test
    fun manualBenchmark() {
        val iterations = 1000
        val startTime = System.nanoTime()

        repeat(iterations) {
            val find = readRawResourceAsModelIndexArray(context, "indexes.dat")
                .asSequence()
                .filter { it.surah == 2 }
                .toList()

            val data = NativeUtils.readRawResourceAsStringArray(context, "english.dat")!!
                .slice((find.first().id - 1) until find.last().id)

            val final = find.mapIndexed { index, modelIndex ->
                ModelQuran(modelIndex.id, listOf(data[index]))
            }
        }

        val endTime = System.nanoTime()
        val totalTime = endTime - startTime
        println("Average execution time: ${(totalTime / iterations) / 1_000_000} ms")
    }
}