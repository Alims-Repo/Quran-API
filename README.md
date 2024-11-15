# Quran API

The `quran-api` is a feature-rich Android library designed to access, search, and manage Quranic data, including translations, Surahs, Juz, pages, Ayahs, and now audio playback. This library provides developers with a structured API for accessing the Quran's data in an efficient and memory-friendly way, leveraging binary file storage for faster data retrieval.

## Versioning and Compatibility

<div>

<table>
  <tr>
    <th align="center">Platform</th>
    <th align="center">Min API</th>
    <th align="center">Version</th>
    <th align="center">License</th>
  </tr>
  <tr>
    <td align="center"><img src="https://img.shields.io/badge/-Android-3DDC84?logo=android&logoColor=white&style=flat-square"></td>
    <td align="center"><img src="https://img.shields.io/badge/-21%2B-brightgreen.svg?style=flat-square"></td>
    <td align="center"><img src="https://img.shields.io/maven-central/v/io.github.alims-repo/quran-api.svg?label=&color=FF6D00&style=flat-square"></td>
    <td align="center"><img src="https://img.shields.io/github/license/Alims-Repo/Quran-API.svg?label=&color=007BFF&style=flat-square"></td>
  </tr>
</table>

</div>


## Features

- **Access to Quranic Data**: Retrieve Surahs, Juz, Pages, Ayahs, and Translations easily.
- **Translation Management**: Download, update, and manage translations with support for multiple languages.
- **Optimized Storage**: Binary file storage for quick access and memory efficiency.
- **Easy Data Queries**: Simple and intuitive API for querying Quranic data.
- **Extensible Architecture**: Modular and interface-based design for easy customization.
- **Quran Audio Playback**: Stream or play audio from a local file with support for multiple Qaris. The API can auto-download audio files as needed and prioritize playback from local storage for offline accessibility.

## Table of Contents

- [Installation](#installation)
- [Quick Start](#quick-start)
- [Usage](#usage)
  - [Initialization](#initialization)
  - [Accessing Quranic Data](#accessing-quranic-data)
  - [Managing Translations](#managing-translations)
  - [Downloading Translations](#downloading-translations)
- [Audio Playback](#audio-playback)
  - [Playing Quran Audio](#playing-quran-audio)
  - [Managing Qaris](#managing-qaris)
- [Interfaces Overview](#interfaces-overview)
- [Core Classes](#core-classes)
- [JNI Integration](#jni-integration)
- [Contributing](#contributing)
- [License](#license)

## Installation

To add the `quran-api` library to your Android project, add the following Maven Central dependency to your project.

### Step 1: Add the Dependency
Add the `quran-api` dependency to your `build.gradle` file:
```kotlin
implementation("io.github.alims-repo:quran-api:0.9.4-beta")
```

Alternatively, you can clone the repository and add the `quran-api` module manually:

```bash
git clone https://github.com/Alims-Repo/Quran-API.git
```

## Quick Start

To get started, initialize the `QuranAPI` singleton in your `Application` class, which will automatically set up the required data and repositories.

### Example Application Initialization

```kotlin
import android.app.Application
import com.nelu.quran_api.di.QuranAPI

class MyApp : Application() {
    override fun onCreate() {
        super.onCreate()
        QuranAPI.init(this)
    }
}
```

## Usage

### Initialization

Once you have initialized `QuranAPI` in your application class, you can start accessing various Quranic data types, such as Surahs, Juz, and Translations. This initialization also copies necessary data files to your internal storage if they don’t already exist.

### Accessing Quranic Data

`QuranAPI` provides access to several data types:

- **JUZ**: For Juz-related data.
- **PAGE**: For accessing page-specific Quranic data.
- **SURAH**: For accessing Surah-related data.
- **QURAN**: For retrieving Ayah and Surah data.
- **TRANSLATION**: For managing translations.

#### Example: Retrieving a List of Surahs

```kotlin
val surahList = QuranAPI.SURAH.getSurahList()
surahList.forEach { surah ->
    println("Surah ${surah.number}: ${surah.englishName}")
}
```

#### Example: Retrieving a Juz by ID

```kotlin
val juz = QuranAPI.JUZ.getJuzById(2)
println("Juz ${juz?.number} starts at Ayah ${juz?.startId}")
```

### Managing Translations

You can get both the available and downloaded translations using the `TRANSLATION` interface.

#### Example: Retrieving Available Translations

```kotlin
val translations = QuranAPI.TRANSLATION.getTranslationList()
translations.forEach { translation ->
    println("Translation: ${translation.name} (${translation.language})")
}
```

#### Example: Retrieving Locally Downloaded Translations

```kotlin
val localTranslations = QuranAPI.TRANSLATION.getLocalTranslationList()
localTranslations.forEach { translation ->
    println("Downloaded Translation: ${translation.name}")
}
```

### Downloading Translations

Translations can be downloaded by providing a translation code. You can track the download progress and handle success or failure with a listener.

#### Example: Downloading a Translation

```kotlin
val translationCode = "en_saheeh"
QuranAPI.TRANSLATION.downloadTranslation(translationCode, object : BaseTranslation.TranslationDownloadListener {
    override fun onSuccess() {
        println("Translation downloaded successfully!")
    }

    override fun onFailure(e: Exception) {
        println("Failed to download translation: ${e.message}")
    }

    override fun onProgress(type: BaseTranslation.TranslationDownloadListener.Type, progress: Int) {
        println("Download progress: $progress%")
    }
})
```

## Audio Playback

The `quran-api` now supports Quran audio playback with dynamic downloading and offline play options.

### Playing Quran Audio

You can play audio for a specific Ayah, Surah, or range. Audio playback can automatically download files for offline playback. If an audio file is available locally, it will be used instead of streaming.

#### Example: Playing Audio

```kotlin
QuranAPI.Audio.playAyah(ayahId = 5) // Plays the specified Ayah
```

### Managing Qaris

The API provides support for multiple Qaris. By specifying a Qari, you can play the preferred recitation style.

#### Example: Set Qari and Play

```kotlin
QuranAPI.Audio.setQari("alafasy")
QuranAPI.Audio.playSurah(surahId = 2)
```

## Interfaces Overview

The library is designed with interfaces to ensure flexibility and ease of use. Here’s an overview of the core interfaces:

- **BaseJuz**: Access and manage Juz-related data.
- **BasePage**: Access Quranic data by pages.
- **BaseSurah**: Retrieve and manage Surah-specific data.
- **BaseQuran**: Query Quranic data, including specific Ayahs.
- **BaseTranslation**: Download and manage translations.

Each interface provides standard methods for accessing, searching, and retrieving lists or individual items.

## Core Classes

### QuranAPI

The `QuranAPI` singleton centralizes access to all repository instances and handles data initialization. It provides properties for each type of data:

- **JUZ**
- **PAGE**
- **SURAH**
- **QURAN**
- **TRANSLATION**
- **AUDIO**

### Repository Classes

Repository classes such as `RepositoryJuz`, `RepositorySurah`, `RepositoryQuran`, and `RepositoryTranslation` implement the core data interfaces, providing actual data operations and interactions with DAOs.

### NativeUtils

`NativeUtils` is a utility object with JNI support for reading Quranic data from binary files, enabling efficient data storage and retrieval. This class offers methods like:

- `readStringFromFileDescriptor`: Reads strings from a binary file.
- `readModelIndexListFromFileDescriptor`: Reads data to construct `ModelIndex` objects.
- `readQuranDataFromPaths`: Reads multiple files and returns their contents as 2D arrays.

## JNI Integration

`quran-api` includes JNI (Java Native Interface) integration to provide efficient data access from binary files. This integration allows the library to store and retrieve large datasets, like Quranic translations, in a memory-efficient manner. The native methods are defined in `NativeUtils` and require the `native-lib` library.

### Example JNI Method

```kotlin
val quranData: Array<String>? = NativeUtils.readQuranDataFromPath(context, "arabic.dat")
quranData?.forEach { ayah ->
    println(ayah)
}
```

## Contributing

Contributions are welcome! Please follow these steps to contribute:

1. **Fork the repository**
2. **Create a new branch** (`git checkout -b feature/your-feature`)
3. **Commit your changes** (`git commit -m 'Add your feature'`)
4. **Push to the branch** (`git push origin feature/your-feature`)
5. **Create a pull request**

Make sure to include tests for any new functionality.

## License

This library is licensed under the GNU GENERAL PUBLIC License. See [LICENSE](LICENSE) for more information.
