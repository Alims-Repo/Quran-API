package com.nelu.alquran

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.core.widget.doAfterTextChanged
import androidx.core.widget.doOnTextChanged
import com.nelu.alquran.databinding.ActivitySplashBinding
import com.nelu.quran_api.binary.BinaryQuran
import com.nelu.quran_api.data.repository.base.BaseTranslation
import com.nelu.quran_api.di.QuranAPI
import java.util.ArrayList
import kotlin.time.measureTime

class SplashActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySplashBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySplashBinding.inflate(layoutInflater)
        setContentView(binding.root)

//        QuranAPI.TRANSLATION.downloadTranslation(
//            "sq_nahi", object : BaseTranslation.TranslationDownloadListener {
//
//                override fun onSuccess() {
//                    Log.e("Success", "Success")
//                }
//
//                override fun onFailure(e: Exception) {
//                    Log.e("Failed", e.toString())
//                }
//
//                override fun onProgress(
//                    type: BaseTranslation.TranslationDownloadListener.Type,
//                    progress: Int
//                ) {
//                    Log.e("Progress - $type", "Progress: $progress")
//                }
//
//            }
//        )

        binding.input.doAfterTextChanged {
            measure(false) {
                QuranAPI.QURAN.searchQuran(it.toString(), list)
            }
        }
    }

    private val list = listOf("sq_nahi", "en_sahih")

    public fun function(view: View) {
        when(view.id) {
            // Surah
            binding.getSurahList.id -> measure { QuranAPI.SURAH.getSurahList() }
            binding.getSurahById.id -> measure { QuranAPI.SURAH.getSurahById(30) }
            binding.getSurahForPage.id -> measure { QuranAPI.SURAH.getSurahForPage(10) }
            binding.getSurahForAyah.id -> measure { QuranAPI.SURAH.getSurahForAyah(3857) }
            binding.getSurahByName.id -> measure { QuranAPI.SURAH.getSurahByName(binding.input.text.toString()) }

            // JUZ
            binding.getJuzList.id -> measure { QuranAPI.JUZ.getJuzList() }
            binding.getJuzById.id -> measure { QuranAPI.JUZ.getJuzById(19) }
            binding.getJuzForPage.id -> measure { QuranAPI.JUZ.getJuzForPage(453) }
            binding.getJuzForAyah.id -> measure { QuranAPI.JUZ.getJuzForAyah(4353) }

            // Translation
            binding.getTranslationList.id -> measure { QuranAPI.TRANSLATION.getTranslationList() }
            binding.getLocalTranslationList.id -> measure { QuranAPI.TRANSLATION.getLocalTranslationList() }
            binding.downloadTranslation.id -> measure {
                QuranAPI.TRANSLATION.downloadTranslation(
                    "sq_nahi", object : BaseTranslation.TranslationDownloadListener {
                        override fun onSuccess() {
                            binding.response.text = "Success"
                        }

                        override fun onFailure(e: Exception) {
                            binding.response.text = e.toString()
                        }

                        override fun onProgress(
                            type: BaseTranslation.TranslationDownloadListener.Type,
                            progress: Int
                        ) {
                            binding.response.text = progress.toString()
                        }
                    }
                )
            }

            // Quran
            binding.getQuranList.id -> measure { QuranAPI.QURAN.getQuranList(list) }
            binding.searchQuran.id -> measure(false) {
                QuranAPI.QURAN.searchQuran(binding.input.text.toString(), list)
            }
            binding.getQuranForSurah.id -> measure { QuranAPI.QURAN.getQuranForSurah(2, list) }
            binding.getQuranForJuz.id -> measure { QuranAPI.QURAN.getQuranForJuz(17, list) }
            binding.getQuranForPage.id -> measure { QuranAPI.QURAN.getQuranForPage(308, list) }
            binding.getQuranByAyahId.id -> measure { QuranAPI.QURAN.getQuranByAyahId(5645, list) }
            binding.getQuranForSurahAndAyah.id -> measure { QuranAPI.QURAN.getQuranForSurahAndAyah(2, 107, list) }
        }
    }

    private fun measure(loop: Boolean = true, element: () -> Any?) {
        if (loop) {
            val count  = try {
                binding.input.text.toString().toInt()
            } catch (e: Exception) { 1 }
            measureTime {
                repeat(count) {
                    element()
                }
            }.let { time ->
                binding.time.text = "${time / count}"
                val res = element()
                binding.response.text = if (res is List<*>)
                    res.take(10).toString()
                else res.toString()
            }
        } else {
            val res: Any?
            measureTime {
                res = element()
            }.let { time ->
                binding.time.text = "$time"
                binding.response.text = if (res is List<*>)
                    res.take(5).toString()
                else res.toString()
            }
        }
    }
}