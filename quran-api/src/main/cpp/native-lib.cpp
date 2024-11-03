#include <jni.h>
#include <vector>
#include <string>
#include <android/log.h>

#define LOG_TAG "NativeLib"
#define LOGD(...) __android_log_print(ANDROID_LOG_DEBUG, LOG_TAG, __VA_ARGS__)
#define LOGE(...) __android_log_print(ANDROID_LOG_ERROR, LOG_TAG, __VA_ARGS__)

// Helper function to read an integer as big-endian
int readIntBigEndian(const unsigned char* buffer) {
    return (buffer[0] << 24) | (buffer[1] << 16) | (buffer[2] << 8) | buffer[3];
}

extern "C" JNIEXPORT jobjectArray JNICALL
Java_com_nelu_quran_1api_utils_NativeUtils_readStringListFromRawResource(JNIEnv *env, jobject /* this */, jobject inputStream) {
    jclass inputStreamClass = env->GetObjectClass(inputStream);
    jmethodID readMethod = env->GetMethodID(inputStreamClass, "read", "([B)I");
    jmethodID closeMethod = env->GetMethodID(inputStreamClass, "close", "()V");

    if (readMethod == nullptr || closeMethod == nullptr) {
        LOGE("Failed to find InputStream read/close methods");
        return nullptr;
    }

    // Allocate a buffer to read the file in one go if possible
    jbyteArray bufferArray = env->NewByteArray(8192);
    if (bufferArray == nullptr) {
        LOGE("Failed to allocate bufferArray");
        env->CallVoidMethod(inputStream, closeMethod);
        return nullptr;
    }

    std::vector<unsigned char> fileBuffer;
    int totalBytesRead = 0;

    // Read file in chunks to avoid multiple JNI calls
    int bytesRead;
    while ((bytesRead = env->CallIntMethod(inputStream, readMethod, bufferArray)) > 0) {
        jbyte* buffer = env->GetByteArrayElements(bufferArray, nullptr);
        fileBuffer.insert(fileBuffer.end(), buffer, buffer + bytesRead);
        totalBytesRead += bytesRead;
        env->ReleaseByteArrayElements(bufferArray, buffer, JNI_ABORT);
    }
    env->DeleteLocalRef(bufferArray);
    env->CallVoidMethod(inputStream, closeMethod);

    if (totalBytesRead < 4) {
        LOGE("File too small to contain list size");
        return nullptr;
    }

    size_t offset = 0;
    int listSize = readIntBigEndian(&fileBuffer[offset]);
    offset += 4;

    if (listSize <= 0 || listSize > 10000) {  // Sanity check
        LOGE("Invalid list size: %d", listSize);
        return nullptr;
    }

    // Allocate Java String array for results
    jobjectArray result = env->NewObjectArray(listSize, env->FindClass("java/lang/String"), nullptr);
    if (result == nullptr) {
        LOGE("Failed to allocate Java String array");
        return nullptr;
    }

    // Process each string in the buffer
    for (int i = 0; i < listSize; ++i) {
        if (offset + 4 > totalBytesRead) {
            LOGE("Unexpected end of file when reading string size for element %d", i);
            return result;
        }

        int stringSize = readIntBigEndian(&fileBuffer[offset]);
        offset += 4;

        if (stringSize <= 0 || offset + stringSize > totalBytesRead) {
            LOGE("Invalid string size %d for element %d", stringSize, i);
            return result;
        }

        // Create Java string directly from the buffer
        jstring javaString = env->NewStringUTF(reinterpret_cast<const char*>(&fileBuffer[offset]));
        if (javaString == nullptr) {
            LOGE("Failed to create Java String for element %d", i);
            return result;
        }

        env->SetObjectArrayElement(result, i, javaString);
        env->DeleteLocalRef(javaString); // Free local reference

        offset += stringSize;
    }

    return result;
}