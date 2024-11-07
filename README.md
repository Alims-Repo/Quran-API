
# Quran API

A comprehensive Android library to access, search, and manage Quranic data, including translations, Surahs, Juz, pages, and Ayahs. The `quran-api` library provides a set of interfaces and repository classes that allow you to interact with Quranic data in a structured and efficient way. It includes support for binary file storage and retrieval, ensuring optimal performance for large datasets.

## Features

- **Quranic Data Access**: Retrieve data for Surahs, Juz, Pages, Ayahs, and Translations.
- **Translation Management**: Download and manage multiple translations.
- **Efficient Storage**: Binary file storage for quick and memory-efficient access.
- **Easy Integration**: Simple interfaces for accessing and querying Quranic data.

## Table of Contents

- [Installation](#installation)
- [Usage](#usage)
  - [Initialization](#initialization)
  - [Accessing Quranic Data](#accessing-quranic-data)
  - [Downloading Translations](#downloading-translations)
- [Interfaces](#interfaces)
- [Classes and Functions](#classes-and-functions)
- [Contributing](#contributing)
- [License](#license)

## Installation

Clone the repository and include the `quran-api` module in your Android project.

```bash
git clone https://github.com/yourusername/quran-api.git
```

Then, add the module to your Android project as a dependency.

## Usage

### Initialization

To start using the library, initialize the `QuranAPI` singleton object in your `Application` class or the entry point of your app.

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

### Accessing Quranic Data

The `QuranAPI` provides access to various Quranic data types through the following properties:

- `JUZ`: Access Juz-related data.
- `PAGE`: Access page-specific Quranic data.
- `SURAH`: Access Surah-related data.
- `QURAN`: Retrieve Ayah and Surah data.
- `TRANSLATION`: Manage and download translations.

Here’s an example of retrieving a list of Surahs:

```kotlin
val surahList = QuranAPI.SURAH.getSurahList()
surahList.forEach { surah ->
    println("Surah Name: ${surah.englishName}")
}
```

### Downloading Translations

You can download translations by providing a translation code. This example demonstrates downloading a translation with a listener to track progress and handle success or failure.

```kotlin
val translationCode = "en_saheeh"
QuranAPI.TRANSLATION.downloadTranslation(translationCode, object : BaseTranslation.TranslationDownloadListener {
    override fun onSuccess() {
        println("Translation download successful!")
    }

    override fun onFailure(e: Exception) {
        println("Translation download failed: ${e.message}")
    }

    override fun onProgress(type: BaseTranslation.TranslationDownloadListener.Type, progress: Int) {
        println("Download progress: $progress%")
    }
})
```

## Interfaces

The library defines the following interfaces for structured data access:

- **BaseJuz**: Access and retrieve Juz data.
- **BasePage**: Access and retrieve Quranic data by page.
- **BaseSurah**: Access Surah-specific data.
- **BaseQuran**: Query Quranic data including Ayahs.
- **BaseTranslation**: Manage and download translations.

Each interface provides methods for retrieving lists, searching data, and accessing specific items by ID.

## Classes and Functions

- **QuranAPI**: Singleton object that centralizes data access and repository instances.
- **Repository Classes**: Provide concrete implementations for accessing and managing Quranic data (e.g., `RepositoryJuz`, `RepositorySurah`).
- **NativeUtils**: Utility class with JNI support for optimized data access and binary file storage.
- **Model Classes**: Data models that represent Quranic entities like `ModelJuz`, `ModelSurah`, `ModelQuran`, and `ModelTranslator`.

## Contributing

Contributions are welcome! Please fork the repository and create a pull request with your changes. Make sure to include tests for any new features or bug fixes.

1. Fork the repository
2. Create a new branch (`git checkout -b feature/your-feature`)
3. Commit your changes (`git commit -m 'Add your feature'`)
4. Push to the branch (`git push origin feature/your-feature`)
5. Create a pull request

## License

This library is licensed under the MIT License. See [LICENSE](LICENSE) for more information.
