# Keep Moshi generated adapters
-keepclassmembers class **JsonAdapter {
    public <init>(...);
    public *;
}

# Hilt Reflection
-keep class dagger.hilt.** { *; }
-keep class * extends dagger.hilt.android.internal.managers.ViewComponentManager$FragmentContextWrapper { *; }