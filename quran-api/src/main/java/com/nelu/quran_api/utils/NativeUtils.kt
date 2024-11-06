package com.nelu.quran_api.utils

import android.content.Context
import android.util.Log
import com.nelu.quran_api.data.model.ModelIndex
import java.io.File
import java.io.FileDescriptor
import java.io.FileInputStream
import java.io.FileOutputStream
import java.io.InputStream

object NativeUtils {

    init {
        System.loadLibrary("native-lib")
    }

    external fun readStringFromFileDescriptor(fd: FileDescriptor, fileSize: Long): Array<String>?

    external fun readModelIndexListFromFileDescriptor(fd: FileDescriptor, fileSize: Long): IntArray

    fun readRawResourceAsStringArray(context: Context, assetFileName: String): Array<String>? {
        val fileDir = File(context.filesDir, assetFileName)
        return readStringFromFileDescriptor(
            FileInputStream(fileDir).fd, fileDir.length()
        )
    }

    fun readRawResourceAsModelIndexArray(context: Context, assetFileName: String): List<ModelIndex> {
        val fileDir = File(context.filesDir, assetFileName)
        val flatArray =  readModelIndexListFromFileDescriptor(
            FileInputStream(fileDir).fd, fileDir.length()
        )

        val modelIndexList = ArrayList<ModelIndex>(flatArray.size / 5)
        for (i in flatArray.indices step 5) {
            modelIndexList.add(
                ModelIndex(
                    id = flatArray[i],
                    surah = flatArray[i + 1],
                    juz = flatArray[i + 2],
                    page = flatArray[i + 3],
                    ayahInSurah = flatArray[i + 4]
                )
            )
        }

        return modelIndexList
    }
}