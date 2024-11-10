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
Java_com_nelu_quran_1api_utils_NativeUtils_readStringFromFileDescriptors(JNIEnv *env, jobject /* this */, jobjectArray fdArray, jlongArray fileSizeArray) {
    jsize count = env->GetArrayLength(fdArray);

    // Verify fileSizeArray has the same length
    if (env->GetArrayLength(fileSizeArray) != count) {
        LOGE("Mismatch between file descriptor count and file size count");
        return nullptr;
    }

    // Cache class and method references
    jclass fileDescriptorClass = env->FindClass("java/io/FileDescriptor");
    jfieldID descriptorField = env->GetFieldID(fileDescriptorClass, "descriptor", "I");
    jclass stringArrayClass = env->FindClass("[Ljava/lang/String;");
    jclass stringClass = env->FindClass("java/lang/String");

    // Get the file sizes
    jlong* fileSizes = env->GetLongArrayElements(fileSizeArray, nullptr);

    // Create the outer array to hold results for each file
    jobjectArray outerArray = env->NewObjectArray(count, stringArrayClass, nullptr);

    for (jsize i = 0; i < count; ++i) {
        // Get the file descriptor object and descriptor integer
        jobject fileDescriptor = env->GetObjectArrayElement(fdArray, i);
        int fd = env->GetIntField(fileDescriptor, descriptorField);

        if (fd < 0) {
            LOGE("Invalid file descriptor at index %d", i);
            continue;
        }

        // Memory-map the file
        unsigned char* mappedData = static_cast<unsigned char*>(mmap(nullptr, fileSizes[i], PROT_READ, MAP_SHARED, fd, 0));
        if (mappedData == MAP_FAILED) {
            LOGE("Memory mapping failed for file %d", i);
            continue;
        }

        // Read list size
        size_t offset = 0;
        int listSize = readIntBigEndian(mappedData);
        offset += 4;

        // Validate list size
        if (listSize <= 0 || listSize > 10000) {
            LOGE("Invalid list size for file %d: %d", i, listSize);
            munmap(mappedData, fileSizes[i]);
            continue;
        }

        // Create the inner array to store each string
        jobjectArray innerArray = env->NewObjectArray(listSize, stringClass, nullptr);

        for (int j = 0; j < listSize; ++j) {
            // Bounds check before reading the string size
            if (offset + 4 > fileSizes[i]) {
                LOGE("Unexpected end of file for element %d in file %d", j, i);
                break;
            }

            int stringSize = readIntBigEndian(&mappedData[offset]);
            offset += 4;

            // Validate string size
            if (stringSize <= 0 || offset + stringSize > fileSizes[i]) {
                LOGE("Invalid string size %d for element %d in file %d", stringSize, j, i);
                break;
            }

            // Create Java String from mapped memory
            jstring javaString = env->NewStringUTF(reinterpret_cast<const char*>(&mappedData[offset]));
            if (javaString == nullptr) {
                LOGE("Failed to create Java String for element %d in file %d", j, i);
                break;
            }

            env->SetObjectArrayElement(innerArray, j, javaString);
            env->DeleteLocalRef(javaString);
            offset += stringSize;
        }

        env->SetObjectArrayElement(outerArray, i, innerArray);
        env->DeleteLocalRef(innerArray);
        munmap(mappedData, fileSizes[i]);
    }

    // Release the fileSizes array
    env->ReleaseLongArrayElements(fileSizeArray, fileSizes, 0);

    return outerArray;
}

// Global references for the ModelIndex class and constructor, cached in JNI_OnLoad
jclass modelIndexClassGlobal;
jmethodID modelIndexConstructorGlobal;

extern "C" jint JNI_OnLoad(JavaVM* vm, void* reserved) {
    JNIEnv* env;
    if (vm->GetEnv(reinterpret_cast<void**>(&env), JNI_VERSION_1_6) != JNI_OK) {
        return -1;
    }

    // Cache the ModelIndex class and constructor for efficient reuse
    jclass localClass = env->FindClass("com/nelu/quran_api/data/model/ModelIndex");
    modelIndexClassGlobal = reinterpret_cast<jclass>(env->NewGlobalRef(localClass));
    modelIndexConstructorGlobal = env->GetMethodID(modelIndexClassGlobal, "<init>", "(IIIII)V");

    env->DeleteLocalRef(localClass);
    return JNI_VERSION_1_6;
}

extern "C" void JNI_OnUnload(JavaVM* vm, void* reserved) {
    JNIEnv* env;
    vm->GetEnv(reinterpret_cast<void**>(&env), JNI_VERSION_1_6);
    env->DeleteGlobalRef(modelIndexClassGlobal);
}

extern "C" JNIEXPORT jintArray JNICALL
Java_com_nelu_quran_1api_utils_NativeUtils_readModelIndexListFromFileDescriptor(JNIEnv *env, jobject /* this */, jobject fileDescriptor, jlong fileSize) {
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

    // Use madvise for optimized sequential access
    madvise(mappedData, fileSize, MADV_SEQUENTIAL);

    // Read list size
    size_t offset = 0;
    int listSize = readIntBigEndian(mappedData);
    offset += 4;

    // Sanity check on list size
    if (listSize <= 0 || listSize > 10000) {
        LOGE("Invalid list size: %d", listSize);
        munmap(mappedData, fileSize);
        return nullptr;
    }

    // Prepare a flat jintArray to hold all ModelIndex entries
    jintArray resultArray = env->NewIntArray(listSize * 5);  // 5 integers per ModelIndex entry
    if (resultArray == nullptr) {
        LOGE("Failed to allocate result array");
        munmap(mappedData, fileSize);
        return nullptr;
    }

    // Get pointer to jintArray to directly set elements
    jint *tempData = env->GetIntArrayElements(resultArray, nullptr);
    if (tempData == nullptr) {
        LOGE("Failed to access result array elements");
        munmap(mappedData, fileSize);
        return nullptr;
    }

    // Read each ModelIndex entry directly into the flat jint array
    for (int i = 0; i < listSize; ++i) {
        if (offset + 20 > fileSize) {  // Ensure there's enough data for each entry (5 integers)
            LOGE("Unexpected end of file when reading ModelIndex element %d", i);
            env->ReleaseIntArrayElements(resultArray, tempData, 0);
            munmap(mappedData, fileSize);
            return nullptr;
        }

        // Populate the data for each ModelIndex entry
        tempData[i * 5 + 0] = readIntBigEndian(&mappedData[offset]); // id
        offset += 4;
        tempData[i * 5 + 1] = readIntBigEndian(&mappedData[offset]); // surah
        offset += 4;
        tempData[i * 5 + 2] = readIntBigEndian(&mappedData[offset]); // juz
        offset += 4;
        tempData[i * 5 + 3] = readIntBigEndian(&mappedData[offset]); // page
        offset += 4;
        tempData[i * 5 + 4] = readIntBigEndian(&mappedData[offset]); // ayahInSurah
        offset += 4;
    }

    // Commit changes to resultArray and release resources
    env->ReleaseIntArrayElements(resultArray, tempData, 0);
    munmap(mappedData, fileSize);

    return resultArray;
}