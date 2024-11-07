package com.nelu.quran_api.utils

import android.content.Context
import com.nelu.quran_api.data.model.ModelIndex
import java.io.File
import java.io.FileDescriptor
import java.io.FileInputStream

/**
 * # NativeUtils
 *
 * A utility object for handling file operations with native (JNI) support. This class provides methods
 * to read data from file descriptors in binary form, specifically for Quranic text and index data.
 * The JNI native library `native-lib` is loaded at initialization.
 */
object NativeUtils {

    init {
        System.loadLibrary("native-lib")
    }

    /**
     * Reads an array of strings from a file descriptor.
     *
     * @param fd The file descriptor pointing to the file.
     * @param fileSize The size of the file in bytes.
     * @return An array of strings read from the file, or `null` if reading fails.
     */
    external fun readStringFromFileDescriptor(fd: FileDescriptor, fileSize: Long): Array<String>?

    /**
     * Reads an array of integers representing `ModelIndex` properties from a file descriptor.
     *
     * @param fd The file descriptor pointing to the file.
     * @param fileSize The size of the file in bytes.
     * @return An `IntArray` containing data for constructing `ModelIndex` objects.
     */
    external fun readModelIndexListFromFileDescriptor(fd: FileDescriptor, fileSize: Long): IntArray

    /**
     * Reads arrays of strings from multiple file descriptors.
     *
     * @param fd An array of file descriptors, each pointing to a file.
     * @param fileSize An array of file sizes corresponding to each file descriptor.
     * @return A 2D array where each element is an array of strings from each file.
     */
    external fun readStringFromFileDescriptors(fd: Array<FileDescriptor>, fileSize: LongArray): Array<Array<String>>

    /**
     * Reads multiple Quranic translation files from given file paths and returns the contents as a 2D array of strings.
     *
     * @param context The application context for accessing internal storage.
     * @param fileNames A list of file names within the internal storage directory.
     * @return A 2D array where each element is an array of strings representing the content of a file.
     */
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

    /**
     * Reads the contents of a single Quranic translation file as an array of strings.
     *
     * @param context The application context for accessing internal storage.
     * @param fileName The name of the file within the internal storage directory.
     * @return An array of strings representing the file content, or `null` if reading fails.
     */
    fun readQuranDataFromPath(context: Context, fileName: String): Array<String>? {
        val fileDir = File(context.filesDir, fileName)
        return readStringFromFileDescriptor(
            FileInputStream(fileDir).fd, fileDir.length()
        )
    }

    /**
     * Reads binary index data from a file and converts it into a list of [ModelIndex] objects.
     *
     * This method reads the raw binary data for each `ModelIndex` property and organizes it into
     * structured [ModelIndex] objects.
     *
     * @param context The application context for accessing internal storage.
     * @param fileNames The name of the file containing the binary index data.
     * @return A list of [ModelIndex] objects, each representing an index entry in the Quran.
     */
    fun readRawResourceAsModelIndexArray(context: Context, fileNames: String): List<ModelIndex> {
        val fileDir = File(context.filesDir, fileNames)
        val flatArray = readModelIndexListFromFileDescriptor(
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