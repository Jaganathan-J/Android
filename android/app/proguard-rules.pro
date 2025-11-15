# ProGuard rules for DynamicMaterialThemeSync
-dontobfuscate
-keep class com.squareup.moshi.** { *; }
-keepclassmembers class ** {
    @com.squareup.moshi.Json* *;
}