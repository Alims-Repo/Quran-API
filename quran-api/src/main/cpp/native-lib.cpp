#include <jni.h>
#include <vector>
#include <string>
#include <android/log.h>

#define LOG_TAG "NativeLib"
#define LOGD(...) __android_log_print(ANDROID_LOG_DEBUG, LOG_TAG, __VA_ARGS__)
#define LOGE(...) __android_log_print(ANDROID_LOG_ERROR, LOG_TAG, __VA_ARGS__)

// Set reasonable maximums
const int MAX_STRING_SIZE = 10240;    // 10KB
const int MAX_LIST_SIZE = 10000;      // Max number of strings expected

// Helper function to read an integer as big-endian from a byte array
int readIntBigEndian(jbyte* buffer) {
    return (static_cast<unsigned char>(buffer[0]) << 24) |
           (static_cast<unsigned char>(buffer[1]) << 16) |
           (static_cast<unsigned char>(buffer[2]) << 8) |
           (static_cast<unsigned char>(buffer[3]));
}

extern "C" JNIEXPORT jobjectArray JNICALL
Java_com_nelu_quran_1api_utils_NativeUtils_readStringListFromRawResource(JNIEnv *env, jobject /* this */, jobject inputStream) {
    jclass inputStreamClass = env->GetObjectClass(inputStream);
    if (inputStreamClass == nullptr) {
        LOGE("Failed to get InputStream class");
        return nullptr;
    }

    jmethodID readMethod = env->GetMethodID(inputStreamClass, "read", "([B)I");
    jmethodID closeMethod = env->GetMethodID(inputStreamClass, "close", "()V");

    if (readMethod == nullptr || closeMethod == nullptr) {
        LOGE("Failed to find InputStream read/close methods");
        return nullptr;
    }

    int listSize = 0;
    {
        jbyteArray buffer = env->NewByteArray(4);
        if (buffer == nullptr) {
            LOGE("Failed to allocate buffer for list size");
            return nullptr;
        }

        int bytesRead = env->CallIntMethod(inputStream, readMethod, buffer);
        if (bytesRead != 4) {
            LOGE("Failed to read list size: bytes read %d", bytesRead);
            env->DeleteLocalRef(buffer);
            env->CallVoidMethod(inputStream, closeMethod);
            return nullptr;
        }

        jbyte* byteBuffer = env->GetByteArrayElements(buffer, nullptr);
        listSize = readIntBigEndian(byteBuffer);
        env->ReleaseByteArrayElements(buffer, byteBuffer, JNI_ABORT);
        env->DeleteLocalRef(buffer);

        // Sanity check for list size
        if (listSize <= 0 || listSize > MAX_LIST_SIZE) {
            LOGE("Invalid list size: %d (out of bounds)", listSize);
            env->CallVoidMethod(inputStream, closeMethod);
            return nullptr;
        }
    }

    LOGD("List size: %d", listSize);
    std::vector<std::string> stringList;
    std::vector<int> stringSizes;

    for (int i = 0; i < listSize; ++i) {
        int stringSize = 0;
        jbyteArray buffer = env->NewByteArray(4);
        if (buffer == nullptr) {
            LOGE("Failed to allocate buffer for string size");
            env->CallVoidMethod(inputStream, closeMethod);
            return nullptr;
        }

        int bytesRead = env->CallIntMethod(inputStream, readMethod, buffer);
        if (bytesRead != 4) {
            LOGE("Failed to read string size for element %d: bytes read %d", i, bytesRead);
            env->DeleteLocalRef(buffer);
            env->CallVoidMethod(inputStream, closeMethod);
            return nullptr;
        }

        jbyte* byteBuffer = env->GetByteArrayElements(buffer, nullptr);
        stringSize = readIntBigEndian(byteBuffer);
        env->ReleaseByteArrayElements(buffer, byteBuffer, JNI_ABORT);
        env->DeleteLocalRef(buffer);

        // Check for reasonable string size
        if (stringSize <= 0 || stringSize > MAX_STRING_SIZE) {
            LOGE("Invalid string size for element %d: %d (out of bounds)", i, stringSize);
            env->CallVoidMethod(inputStream, closeMethod);
            return nullptr;
        }

        jbyteArray stringBuffer = env->NewByteArray(stringSize);
        if (stringBuffer == nullptr) {
            LOGE("Failed to allocate buffer for string data of size %d", stringSize);
            env->CallVoidMethod(inputStream, closeMethod);
            return nullptr;
        }

        bytesRead = env->CallIntMethod(inputStream, readMethod, stringBuffer);
        if (bytesRead != stringSize) {
            LOGE("Failed to read string data for element %d: bytes read %d", i, bytesRead);
            env->DeleteLocalRef(stringBuffer);
            env->CallVoidMethod(inputStream, closeMethod);
            return nullptr;
        }

        jbyte* stringData = env->GetByteArrayElements(stringBuffer, nullptr);
        std::string str(reinterpret_cast<char*>(stringData), stringSize);
        env->ReleaseByteArrayElements(stringBuffer, stringData, JNI_ABORT);
        env->DeleteLocalRef(stringBuffer);

        stringList.push_back(str);
        stringSizes.push_back(stringSize);
    }

    env->CallVoidMethod(inputStream, closeMethod);

    // Convert the C++ vector to a Java array
    jobjectArray result = env->NewObjectArray(stringList.size(), env->FindClass("java/lang/String"), nullptr);
    if (result == nullptr) {
        LOGE("Failed to create Java String array");
        return nullptr;
    }

    for (size_t i = 0; i < stringList.size(); ++i) {
        jstring javaString = env->NewStringUTF(stringList[i].c_str());
        if (javaString == nullptr) {
            LOGE("Failed to create Java string for element %zu", i);
            continue;
        }
        env->SetObjectArrayElement(result, i, javaString);
        env->DeleteLocalRef(javaString);
    }

    LOGD("Successfully read all data from resource");
    return result;
}