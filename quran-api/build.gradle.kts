import com.vanniktech.maven.publish.SonatypeHost

plugins {
    alias(libs.plugins.android.library)
    alias(libs.plugins.kotlin.android)
    id("com.vanniktech.maven.publish") version "0.28.0"
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

    implementation("io.github.alims-repo:nc-base:0.3.6-alpha")

    implementation(libs.androidx.media)
    implementation(libs.androidx.media3.session)
    implementation(libs.androidx.media3.exoplayer)

    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
}

mavenPublishing {
    coordinates(
        groupId = "io.github.alims-repo",
        artifactId = "quran-api",
        version = "0.9.8-beta"
    )

    pom {
        name.set("Quran API")
        description.set("Quran API is a library that provides an easy way to access the Quran data.")
        inceptionYear.set("2024")
        url.set("https://github.com/Alims-Repo/Quran-API")

        licenses {
            license {
                name.set("GNU GENERAL PUBLIC LICENSE")
                url.set("https://www.gnu.org/licenses/gpl-3.0.en.html")
            }
        }

        developers {
            developer {
                id.set("alim")
                name.set("Alim Sourav")
                email.set("sourav.0.alim@gmail.com")
            }
        }

        scm {
            url.set("https://github.com/Alims-Repo/Quran-API")
        }
    }

    publishToMavenCentral(SonatypeHost.CENTRAL_PORTAL)

    signAllPublications()
}
