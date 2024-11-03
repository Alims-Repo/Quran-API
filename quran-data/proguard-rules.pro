# Keep all classes in the com.nelu.quran_data.di package
-keep class com.nelu.quran_data.di.** { *; }

# Example for a specific class mentioned in the missing rules
-keep class com.nelu.quran_data.di.BaseData { *; }
-keep class com.nelu.quran_data.di.QuranData { *; }
 -keep class com.nelu.quran_data.data.repo.base.BaseQuran { *;}

-dontwarn java.lang.invoke.StringConcatFactory
