# Keep all classes and their members within the specified package
-keep class com.nelu.quran_api.data.repository.base.** {
    *;
}

-keep class com.nelu.quran_api.di.QuranAPI {
    *;
}


-dontwarn java.lang.invoke.StringConcatFactory