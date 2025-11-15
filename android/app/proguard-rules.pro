# ProGuard rules for MaterialShowcase
# Keep Hilt generated classes
-keep class dagger.hilt.** { *; }
-keep class * extends dagger.hilt.internal.GeneratedComponent {
    *;
}
