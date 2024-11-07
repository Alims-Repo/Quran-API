import java.util.Properties

plugins {
    alias(libs.plugins.android.library)
    alias(libs.plugins.kotlin.android)
    id("maven-publish")
}

android {
    namespace = "com.nelu.quran_api"
    compileSdk = 35

    defaultConfig {
        minSdk = 21

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        consumerProguardFiles("consumer-rules.pro")
    }

    buildTypes {
        release {
            isMinifyEnabled = true
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }

    externalNativeBuild {
        cmake {
            path("CMakeLists.txt")
        }
    }

    ndkVersion = "28.0.12433566"

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }

    kotlinOptions {
        jvmTarget = "11"
    }
}

dependencies {
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.appcompat)

    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
}

val credentialsProperties = Properties().apply {
    val file = file("credentials.properties")
    if (file.exists()) {
        load(file.inputStream())
    }
}

publishing {
    publications {
        create<MavenPublication>("bar") {
            groupId = "com.nelu"
            artifactId = "quran-api"
            version = "0.9.1-beta"

            artifact("${buildDir}/outputs/aar/quran-api-release.aar")
        }

        repositories {
            maven {
                name = "GithubPackages"
                url = uri("https://maven.pkg.github.com/Alims-Repo/Al-Quran-API")
                credentials {
                    username = credentialsProperties.getProperty("gpr.user")
                    password = credentialsProperties.getProperty("gpr.token")
                }
            }
        }
    }
}
