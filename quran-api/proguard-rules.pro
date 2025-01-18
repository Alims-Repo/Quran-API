# Keep all classes and their members within the specified package
-keep class com.nelu.quran_api.data.repository.base.** {
    *;
}

-keep class com.nelu.quran_api.di.QuranAPI {
    *;
}

-keep class com.nelu.quran_api.data.model.** {
    *;
}

-dontwarn java.lang.invoke.StringConcatFactory
-repackageclasses 'io.github.alims.quran.api'