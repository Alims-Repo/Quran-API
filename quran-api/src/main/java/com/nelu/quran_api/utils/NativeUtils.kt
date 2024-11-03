package com.nelu.quran_api.utils

import android.content.Context
import android.util.Log
import java.io.File
import java.io.InputStream

object NativeUtils {

    init {
        System.loadLibrary("native-lib")
    }

    external fun readStringFromStream(inputStream: InputStream): Array<String>?
}

// Usage
fun Context.readBinaryDataFromResource(resourceId: Int): Array<String>? {
    val inputStream = resources.openRawResource(resourceId)
    return NativeUtils.readStringFromStream(inputStream)
}