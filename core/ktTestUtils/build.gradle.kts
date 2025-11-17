plugins {
    alias(libs.plugins.android.library)
    alias(libs.plugins.kotlin.android)
}

apply(from = rootProject.file("gradle/android_common_config.gradle"))

android {
    namespace = "com.example.core.ktTestUtils"
}

dependencies {

    implementation(libs.junit)
    implementation(libs.coroutines.test)
    implementation(libs.coroutines.core)
}
