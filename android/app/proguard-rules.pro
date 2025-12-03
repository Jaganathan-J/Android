# Add strict rules for R8/ProGuard
-keep class com.example.fitlife.** { *; }
-dontwarn dagger.**
-dontwarn javax.inject.**