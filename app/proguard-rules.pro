### Classes
-keep class com.example.omdb.domain.model.** { *; }
-keep class com.example.omdb.data.local.model.** { *; }
-keep class com.example.omdb.data.network.model.** { *; }
-keepnames class com.example.omdb.util.** { *; }

### ServiceLoader Support
-keepnames class kotlinx.coroutines.internal.MainDispatcherFactory {}
-keepnames class kotlinx.coroutines.CoroutineExceptionHandler {}
### Most of volatile fields are updated with AFU and should not be mangled
-keepclassmembernames class kotlinx.** { volatile <fields>; }

### Core Components
-keep class androidx.core.app.** { *; }

### Ktor
-keep class io.ktor.** { volatile <fields>; }
-dontwarn java.lang.management.**
-dontwarn org.slf4j.**

## Gson
# Gson uses generic type information stored in a class file when working with fields. Proguard
# removes such information by default, so configure it to keep all of it.
-keepattributes Signature
# For using GSON @Expose annotation
-keepattributes *Annotation*
# Gson specific classes
-dontwarn sun.misc.**
# Application classes that will be serialized/deserialized over Gson
-keep class com.google.gson.examples.android.model.** { <fields>; }
# Prevent proguard from stripping interface information from TypeAdapter, TypeAdapterFactory,
# JsonSerializer, JsonDeserializer instances (so they can be used in @JsonAdapter)
-keep class * extends com.google.gson.TypeAdapter
-keep class * implements com.google.gson.TypeAdapterFactory
-keep class * implements com.google.gson.JsonSerializer
-keep class * implements com.google.gson.JsonDeserializer
# Prevent R8 from leaving Data object members always null
-keepclassmembers,allowobfuscation class * {
  @com.google.gson.annotations.SerializedName <fields>;
}
# Retain generic signatures of TypeToken and its subclasses with R8 version 3.0 and higher.
-keep,allowobfuscation,allowshrinking class com.google.gson.reflect.TypeToken
-keep,allowobfuscation,allowshrinking class * extends com.google.gson.reflect.TypeToken