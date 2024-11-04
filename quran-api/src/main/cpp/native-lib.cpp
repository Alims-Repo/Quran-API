#include <jni.h>
#include <fcntl.h>
#include <sys/mman.h>
#include <unistd.h>
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
Java_com_nelu_quran_1api_utils_NativeUtils_readStringFromFileDescriptor(JNIEnv *env, jobject /* this */, jobject fileDescriptor, jlong fileSize) {
    // Extract the integer file descriptor from the FileDescriptor object
    jclass fileDescriptorClass = env->GetObjectClass(fileDescriptor);
    jfieldID descriptorField = env->GetFieldID(fileDescriptorClass, "descriptor", "I");
    int fd = env->GetIntField(fileDescriptor, descriptorField);

    if (fd < 0) {
        LOGE("Invalid file descriptor");
        return nullptr;
    }

    // Map the file into memory
    unsigned char* mappedData = static_cast<unsigned char*>(mmap(nullptr, fileSize, PROT_READ, MAP_SHARED, fd, 0));
    if (mappedData == MAP_FAILED) {
        LOGE("Memory mapping failed");
        return nullptr;
    }

    // Start reading the list from the mapped memory
    size_t offset = 0;
    int listSize = readIntBigEndian(mappedData);
    offset += 4;

    if (listSize <= 0 || listSize > 10000) {  // Sanity check
        LOGE("Invalid list size: %d", listSize);
        munmap(mappedData, fileSize);
        return nullptr;
    }

    // Create a Java String array to hold the results
    jobjectArray result = env->NewObjectArray(listSize, env->FindClass("java/lang/String"), nullptr);
    if (result == nullptr) {
        LOGE("Failed to allocate Java String array");
        munmap(mappedData, fileSize);
        return nullptr;
    }

    // Read each string from the mapped memory
    for (int i = 0; i < listSize; ++i) {
        if (offset + 4 > fileSize) {
            LOGE("Unexpected end of file when reading string size for element %d", i);
            munmap(mappedData, fileSize);
            return result;
        }

        int stringSize = readIntBigEndian(&mappedData[offset]);
        offset += 4;

        if (stringSize <= 0 || offset + stringSize > fileSize) {
            LOGE("Invalid string size %d for element %d", stringSize, i);
            munmap(mappedData, fileSize);
            return result;
        }

        jstring javaString = env->NewStringUTF(reinterpret_cast<const char*>(&mappedData[offset]));
        if (javaString == nullptr) {
            LOGE("Failed to create Java String for element %d", i);
            munmap(mappedData, fileSize);
            return result;
        }

        env->SetObjectArrayElement(result, i, javaString);
        env->DeleteLocalRef(javaString);

        offset += stringSize;
    }

    // Unmap memory but do not close the file descriptor
    munmap(mappedData, fileSize);

    return result;
}