
# Quran API

The `quran-api` is a feature-rich Android library designed to access, search, and manage Quranic data, including translations, Surahs, Juz, pages, and Ayahs. This library provides developers with a structured API for accessing the Quran's data in an efficient and memory-friendly way, leveraging binary file storage for faster data retrieval.

## Features

- **Access to Quranic Data**: Retrieve Surahs, Juz, Pages, Ayahs, and Translations easily.
- **Translation Management**: Download, update, and manage translations with support for multiple languages.
- **Optimized Storage**: Binary file storage for quick access and memory efficiency.
- **Easy Data Queries**: Simple and intuitive API for querying Quranic data.
- **Extensible Architecture**: Modular and interface-based design for easy customization.

## Table of Contents

- [Installation](#installation)
- [Quick Start](#quick-start)
- [Usage](#usage)
  - [Initialization](#initialization)
  - [Accessing Quranic Data](#accessing-quranic-data)
  - [Managing Translations](#managing-translations)
  - [Downloading Translations](#downloading-translations)
- [Interfaces Overview](#interfaces-overview)
- [Core Classes](#core-classes)
- [JNI Integration](#jni-integration)
- [Contributing](#contributing)
- [License](#license)

## Installation

Clone the repository and add the `quran-api` module to your Android project.

```bash
git clone https://github.com/Alims-Repo/Al-Quran-API.git
```

Add the `quran-api` module as a dependency in your Android project.

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

---

With this extended documentation, developers have a comprehensive guide to getting started, using, and contributing to the `quran-api` library. It includes detailed examples and explanations of the core components and interfaces, making integration and development straightforward.
