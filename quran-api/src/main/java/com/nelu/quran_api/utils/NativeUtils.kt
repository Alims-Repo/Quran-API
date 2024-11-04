package com.nelu.quran_api.utils

import android.content.Context
import android.util.Log
import java.io.File
import java.io.FileDescriptor
import java.io.FileInputStream
import java.io.FileOutputStream
import java.io.InputStream

object NativeUtils {

    init {
        System.loadLibrary("native-lib") // Ensure this matches your .so file name
    }

    // Native method that accepts a FileDescriptor and file size
    external fun readStringFromFileDescriptor(fd: FileDescriptor, fileSize: Long): Array<String>?

    // Helper function to read a raw resource as a String array
    fun readRawResourceAsStringArray(context: Context, assetFileName: String): Array<String>? {

        val cacheFile = File(context.cacheDir, assetFileName)
        val fileSize = cacheFile.length()
        return readStringFromFileDescriptor(FileInputStream(cacheFile).fd, fileSize)


//        val start = System.currentTimeMillis()
//        // Copy the raw resource to a temporary file
//        val tempFile = File.createTempFile("tempfile", null, context.cacheDir)
//        tempFile.deleteOnExit() // Delete the file when the app exits
//
//        // Copy the raw resource content to the temporary file
//        context.resources.openRawResource(rawResourceId).use { inputStream ->
//            FileOutputStream(tempFile).use { outputStream ->
//                inputStream.copyTo(outputStream)
//            }
//        }
//
//        // Get the file size and FileDescriptor from the temporary file
//        val fileSize = tempFile.length()
//        FileInputStream(tempFile).use { inputStream ->
//            Log.e("Time", "Time to copy file: ${System.currentTimeMillis() - start}")
//            return readStringFromFileDescriptor(inputStream.fd, fileSize)
//        }
    }
}