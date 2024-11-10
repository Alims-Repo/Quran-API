package com.nelu.quran_api.data.repository

import android.app.Application
import android.content.Intent
import com.nelu.quran_api.data.repository.base.BaseAudio
import com.nelu.quran_api.service.AudioService

class RepositoryAudio(
    val application: Application
) : BaseAudio {

    override fun playAyah(ayah: Int) {
        val intent = Intent(application, AudioService::class.java)
        application.startService(intent)
    }

    override fun playSurah(surah: Int) {
        TODO("Not yet implemented")
    }

    override fun playJuz(juz: Int) {
        TODO("Not yet implemented")
    }

    override fun playPage(page: Int) {
        TODO("Not yet implemented")
    }

    override fun pause() {
        TODO("Not yet implemented")
    }

    override fun resume(): Boolean {
        TODO("Not yet implemented")
    }

    override fun stop() {
        TODO("Not yet implemented")
    }
}