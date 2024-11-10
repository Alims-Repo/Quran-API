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
}