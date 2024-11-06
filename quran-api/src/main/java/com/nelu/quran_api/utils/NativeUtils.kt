package com.nelu.quran_api.utils

import android.content.Context
import com.nelu.quran_api.data.model.ModelIndex
import java.io.File
import java.io.FileDescriptor
import java.io.FileInputStream

object NativeUtils {

    init {
        System.loadLibrary("native-lib")
    }

    external fun readStringFromFileDescriptor(fd: FileDescriptor, fileSize: Long): Array<String>?

    external fun readModelIndexListFromFileDescriptor(fd: FileDescriptor, fileSize: Long): IntArray

    external fun readStringFromFileDescriptors(fd: Array<FileDescriptor>, fileSize: LongArray): Array<Array<String>>

    fun readQuranDataFromPaths(context: Context, fileNames: List<String>): Array<Array<String>> {
        return readStringFromFileDescriptors(
            fileNames.map { fileName ->
                FileInputStream(File(context.filesDir, fileName)).fd
            }.toTypedArray(),
            fileNames.map { fileName ->
                File(context.filesDir, fileName).length()
            }.toLongArray()
        )
    }

    fun readQuranDataFromPath(context: Context, fileName: String): Array<String>? {
        val fileDir = File(context.filesDir, fileName)
        return readStringFromFileDescriptor(
            FileInputStream(fileDir).fd, fileDir.length()
        )
    }

    fun readRawResourceAsModelIndexArray(context: Context, fileNames: String): List<ModelIndex> {
        val fileDir = File(context.filesDir, fileNames)
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