# ============================================================================
# SmartFinance (CPRecap) R8 / ProGuard rules
# AGP runs R8 in full mode by default. Keep rules below protect reflection-based
# libraries (Gson, Room, Hilt, Firebase, Gmail API) while still obfuscating the
# rest of the app.
# ============================================================================

# --- General / debugging ---------------------------------------------------
# Keep line numbers for readable crash reports, but hide the original file name.
-keepattributes SourceFile,LineNumberTable
-renamesourcefileattribute SourceFile
# Keep annotations used by DI / serialization / Room, etc.
-keepattributes *Annotation*,Signature,InnerClasses,EnclosingMethod,RuntimeVisibleAnnotations,RuntimeVisibleParameterAnnotations

# Strip verbose logging in release builds (reduces info leakage & size).
-assumenosideeffects class android.util.Log {
    public static int v(...);
    public static int d(...);
    public static int i(...);
}

# ============================================================================
# Kotlin
# ============================================================================
-keep class kotlin.Metadata { *; }
-dontwarn kotlin.**
-keepclassmembers class **$WhenMappings { <fields>; }

# Kotlin Coroutines
-dontwarn kotlinx.coroutines.**
-keepclassmembernames class kotlinx.** { volatile <fields>; }

# ============================================================================
# Gson  (models are serialized reflectively -> must be kept)
# ============================================================================
-keepattributes Signature,*Annotation*,EnclosingMethod,InnerClasses
-keep class com.google.gson.** { *; }
-dontwarn com.google.gson.**
-keep class sun.misc.Unsafe { *; }
# Keep generic type information for TypeToken (required by R8 full mode).
-keep,allowobfuscation,allowshrinking class com.google.gson.reflect.TypeToken
-keep,allowobfuscation,allowshrinking class * extends com.google.gson.reflect.TypeToken

# Keep all app data/domain/presentation models & DTOs (serialized by Gson,
# used by Room entities, and passed as prediction results).
-keep class com.yusuffdllh.smartfinance.data.model.** { *; }
-keep class com.yusuffdllh.smartfinance.domain.model.** { *; }
-keep class com.yusuffdllh.smartfinance.data.local.entity.** { *; }
# Prediction result & AI service DTOs (serialized to/from JSON).
-keep class com.yusuffdllh.smartfinance.service.** { *; }
-keepclassmembers class com.yusuffdllh.smartfinance.** {
    @com.google.gson.annotations.SerializedName <fields>;
}

# ============================================================================
# Room
# ============================================================================
-keep class * extends androidx.room.RoomDatabase { *; }
-keep @androidx.room.Entity class * { *; }
-keep @androidx.room.Dao class * { *; }
-dontwarn androidx.room.paging.**

# ============================================================================
# SQLCipher (net.zetetic) + androidx.sqlite support factory
# ============================================================================
-keep class net.zetetic.** { *; }
-keep interface net.zetetic.** { *; }
-keep class androidx.sqlite.** { *; }
-dontwarn net.zetetic.**

# ============================================================================
# Hilt / Dagger
# ============================================================================
-keep class dagger.hilt.** { *; }
-keep class javax.inject.** { *; }
-keep class * extends dagger.hilt.android.lifecycle.HiltViewModel { *; }
-dontwarn dagger.hilt.**
-keep,allowobfuscation @dagger.hilt.android.lifecycle.HiltViewModel class *

# ============================================================================
# WorkManager (Hilt workers instantiated reflectively)
# ============================================================================
-keep class * extends androidx.work.Worker { *; }
-keep class * extends androidx.work.ListenableWorker { *; }
-keepclassmembers class * extends androidx.work.ListenableWorker {
    public <init>(android.content.Context, androidx.work.WorkerParameters);
}

# ============================================================================
# Firebase (Auth + Firestore)
# ============================================================================
-keep class com.google.firebase.** { *; }
-keep class com.google.android.gms.** { *; }
-dontwarn com.google.firebase.**
-dontwarn com.google.android.gms.**
# Firestore serializes model classes via reflection.
-keepclassmembers class com.yusuffdllh.smartfinance.** {
    public <init>();
}

# ============================================================================
# Google API Client / Gmail API
# ============================================================================
-keep class com.google.api.** { *; }
-keep class com.google.api.client.** { *; }
-keep class com.google.api.services.gmail.** { *; }
-keep class com.google.auth.** { *; }
-keepclassmembers class * {
    @com.google.api.client.util.Key <fields>;
}
-dontwarn com.google.api.client.**
-dontwarn com.google.auth.**
-dontwarn org.apache.http.**
-dontwarn com.google.common.**
-dontwarn javax.naming.**

# ============================================================================
# OkHttp / Okio
# ============================================================================
-dontwarn okhttp3.**
-dontwarn okio.**
-keep class okhttp3.** { *; }
-keep class okio.** { *; }

# ============================================================================
# Credentials / Google Identity (Sign-In)
# ============================================================================
-keep class androidx.credentials.** { *; }
-keep class com.google.android.libraries.identity.googleid.** { *; }
-dontwarn androidx.credentials.**

# ============================================================================
# Lottie (parses JSON animations reflectively)
# ============================================================================
-keep class com.airbnb.lottie.** { *; }
-dontwarn com.airbnb.lottie.**

# ============================================================================
# Coil
# ============================================================================
-dontwarn coil.**

# ============================================================================
# Enums (values()/valueOf used reflectively for TransactionType, etc.)
# ============================================================================
-keepclassmembers enum com.yusuffdllh.smartfinance.** {
    public static **[] values();
    public static ** valueOf(java.lang.String);
}

# ============================================================================
# Parcelable / Serializable
# ============================================================================
-keepclassmembers class * implements android.os.Parcelable {
    public static final android.os.Parcelable$Creator CREATOR;
}
-keepnames class * implements java.io.Serializable
-keepclassmembers class * implements java.io.Serializable {
    static final long serialVersionUID;
    private static final java.io.ObjectStreamField[] serialPersistentFields;
    private void writeObject(java.io.ObjectOutputStream);
    private void readObject(java.io.ObjectInputStream);
    java.lang.Object writeReplace();
    java.lang.Object readResolve();
}
