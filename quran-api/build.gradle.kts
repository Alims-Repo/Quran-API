plugins {
    alias(libs.plugins.android.library)
    alias(libs.plugins.kotlin.android)
    id("com.google.protobuf") version "0.9.4"
//    id("com.google.protobuf") version ("0.8.18")
}

android {
    namespace = "com.nelu.quran_api"
    compileSdk = 35

    defaultConfig {
        minSdk = 22

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        consumerProguardFiles("consumer-rules.pro")
    }

    sourceSets {
        getByName("main") {
            java.srcDir("src/main/proto") // Define proto source directory
        }
    }

//    sourceSets {
//        named("main") {
//            kotlin.srcDir("src/main/kotlin")
//            java.srcDir("src/main/java")
////            // Add this line to include your proto directory
////            proto.srcDir("src/main/proto")
//        }
//    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
    kotlinOptions {
        jvmTarget = "11"
    }
}

//protobuf {
//    // Configure protoc artifact for code generation
//    protoc { artifact = "com.google.protobuf:protoc:3.21.1" }
//
//    // Configure the generated output as lite for Android
//    generateProtoTasks {
//        ofSourceSet("main").forEach { task ->
//            task.builtins {
//                // Use "java" and set "lite" option for protobuf-javalite compatibility
//                create("java") {
//                    option("lite")
//                }
//            }
//        }
//    }
//}

protobuf {
    protoc {
        artifact = "com.google.protobuf:protoc:3.21.7"
    }
    plugins {
        generateProtoTasks {
            all().forEach {
                it.builtins {
                    create("java") {
                        option("lite")
                    }
                }
            }
        }
    }
}

dependencies {

    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.appcompat)
    implementation(libs.material)

    implementation("com.google.protobuf:protobuf-javalite:3.21.7")

//    implementation("com.google.protobuf:protobuf-java:4.28.3")
//    implementation("com.google.protobuf:protobuf-kotlin:4.28.3")
//    implementation("com.google.protobuf:protobuf-lite:4.28.3")

    implementation(project(":quran-data"))

    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
}
