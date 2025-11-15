# ProGuard rules for M3Gallery
# Keep Hilt generated components
-keep class dagger.hilt.** { *; }
-keep class * extends dagger.hilt.internal.ComponentEntryPoint { *; }
-keep class * extends dagger.hilt.internal.GeneratedComponent { *; }
