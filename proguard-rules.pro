-keep class com.gameoptimizer.app.** { *; }
-keep class androidx.compose.** { *; }
-keep class kotlin.** { *; }

# Keep all public classes and methods
-keepclassmembers class * {
    public *;
}

# Keep enums
-keepclassmembers enum * {
    public static **[] values();
    public static ** valueOf(java.lang.String);
}

# Remove logging
-assumenosideeffects class android.util.Log {
    public static *** d(...);
    public static *** v(...);
    public static *** i(...);
}