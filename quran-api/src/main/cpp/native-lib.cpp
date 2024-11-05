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

//extern "C" JNIEXPORT jobjectArray JNICALL
//Java_com_nelu_quran_1api_utils_NativeUtils_readModelIndexListFromFileDescriptor(JNIEnv *env, jobject /* this */, jobject fileDescriptor, jlong fileSize) {
//    // Extract the integer file descriptor from the FileDescriptor object
//    jclass fileDescriptorClass = env->GetObjectClass(fileDescriptor);
//    jfieldID descriptorField = env->GetFieldID(fileDescriptorClass, "descriptor", "I");
//    int fd = env->GetIntField(fileDescriptor, descriptorField);
//
//    if (fd < 0) {
//        LOGE("Invalid file descriptor");
//        return nullptr;
//    }
//
//    // Map the file into memory
//    unsigned char* mappedData = static_cast<unsigned char*>(mmap(nullptr, fileSize, PROT_READ, MAP_SHARED, fd, 0));
//    if (mappedData == MAP_FAILED) {
//        LOGE("Memory mapping failed");
//        return nullptr;
//    }
//
//    // Start reading the list from the mapped memory
//    size_t offset = 0;
//    int listSize = readIntBigEndian(mappedData);
//    offset += 4;
//
//    if (listSize <= 0 || listSize > 10000) {  // Sanity check
//        LOGE("Invalid list size: %d", listSize);
//        munmap(mappedData, fileSize);
//        return nullptr;
//    }
//
//    // Locate the ModelIndex class and its constructor
//    jclass modelIndexClass = env->FindClass("com/nelu/quran_api/data/model/ModelIndex");
//    if (modelIndexClass == nullptr) {
//        LOGE("Failed to find ModelIndex class");
//        munmap(mappedData, fileSize);
//        return nullptr;
//    }
//    jmethodID modelIndexConstructor = env->GetMethodID(modelIndexClass, "<init>", "(IIIII)V");
//    if (modelIndexConstructor == nullptr) {
//        LOGE("Failed to find ModelIndex constructor");
//        munmap(mappedData, fileSize);
//        return nullptr;
//    }
//
//    // Create a Java array to hold the ModelIndex objects
//    jobjectArray result = env->NewObjectArray(listSize, modelIndexClass, nullptr);
//    if (result == nullptr) {
//        LOGE("Failed to allocate Java ModelIndex array");
//        munmap(mappedData, fileSize);
//        return nullptr;
//    }
//
//    // Read each ModelIndex entry from the mapped memory
//    for (int i = 0; i < listSize; ++i) {
//        if (offset + 20 > fileSize) {  // 5 integers * 4 bytes each
//            LOGE("Unexpected end of file when reading ModelIndex element %d", i);
//            munmap(mappedData, fileSize);
//            return result;
//        }
//
//        int id = readIntBigEndian(&mappedData[offset]);
//        offset += 4;
//        int surah = readIntBigEndian(&mappedData[offset]);
//        offset += 4;
//        int juz = readIntBigEndian(&mappedData[offset]);
//        offset += 4;
//        int page = readIntBigEndian(&mappedData[offset]);
//        offset += 4;
//        int ayahInSurah = readIntBigEndian(&mappedData[offset]);
//        offset += 4;
//
//        // Create a new ModelIndex object
//        jobject modelIndexObject = env->NewObject(modelIndexClass, modelIndexConstructor, id, surah, juz, page, ayahInSurah);
//        if (modelIndexObject == nullptr) {
//            LOGE("Failed to create ModelIndex object for element %d", i);
//            munmap(mappedData, fileSize);
//            return result;
//        }
//
//        // Set the ModelIndex object in the result array
//        env->SetObjectArrayElement(result, i, modelIndexObject);
//        env->DeleteLocalRef(modelIndexObject);
//    }
//
//    // Unmap memory but do not close the file descriptor
//    munmap(mappedData, fileSize);
//
//    return result;
//}