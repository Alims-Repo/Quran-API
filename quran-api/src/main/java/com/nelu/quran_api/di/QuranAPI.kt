package com.nelu.quran_api.di

import DaoQuranData.ModelQuranData
import android.content.Context
import com.google.protobuf.InvalidProtocolBufferException
import java.io.BufferedInputStream
import java.io.File
import java.io.FileInputStream
import java.io.FileOutputStream
import java.io.RandomAccessFile
import java.nio.ByteBuffer
import java.nio.channels.FileChannel

object QuranAPI {

    fun Context.saveData(list: List<String>) {
        val file = File(filesDir, "model_quran_data")

        FileOutputStream(file, true).use { output ->
            list.forEachIndexed { index, data ->
                val translations = ArrayList<DaoQuranData.ModelTranslation>()

                // Create multiple translations for demonstration purposes
                for (x in 0..20) {
                    translations.add(
                        DaoQuranData.ModelTranslation.newBuilder()
                            .setTranslation(data)
                            .build()
                    )
                }

                // Build ModelQuranData with translations
                val dao = ModelQuranData.newBuilder()
                    .setId(index)
                    .addAllTranslation(translations)
                    .build()

                // Serialize ModelQuranData to bytes
                val daoBytes = dao.toByteArray()

                // Write the length of the serialized data (4 bytes) followed by the serialized data
                output.write(ByteBuffer.allocate(4).putInt(daoBytes.size).array())
                output.write(daoBytes)
            }
        }
    }

    fun Context.loadData(): List<ModelQuranData> {
        val file = File(filesDir, "model_quran_data")
        if (!file.exists()) return emptyList()

        val modelQuranDataList = mutableListOf<ModelQuranData>()

        FileInputStream(file).use { input ->
            val data = input.readBytes()
            var offset = 0

            while (offset < data.size) {
                // Read the length of the next entry (4 bytes as an Int)
                val lengthBuffer = ByteBuffer.wrap(data, offset, 4)
                val entryLength = lengthBuffer.int
                offset += 4

                // Ensure there is enough data left for this entry
                if (offset + entryLength > data.size) {
                    throw InvalidProtocolBufferException("Truncated data: expected $entryLength bytes but found ${data.size - offset}")
                }

                // Read the entry bytes based on the length
                val entryBytes = data.copyOfRange(offset, offset + entryLength)
                offset += entryLength

                // Deserialize the entry
                val dao = ModelQuranData.parseFrom(entryBytes)
                modelQuranDataList.add(dao)
            }
        }
        return modelQuranDataList
    }


//    fun loadData(context: Context): List<DaoQuranData. ModelQuranData>? {
////        val file = File(context.filesDir, "dao_translation_data")
////        if (!file.exists()) return null
////
////        val daoTranslation = BufferedInputStream(FileInputStream(file)).use { input ->
////            DaoTranslationOuterClass.DaoTranslation.parseFrom(input)
////        }
////        return daoTranslation.valuesList
//    }
}