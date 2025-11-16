plugins {
    alias(libs.plugins.android.library)
    alias(libs.plugins.kotlin.android)
}

apply(from = rootProject.file("gradle/android_common_config.gradle"))

android {
    namespace = "com.example.feature.currencyinfo"
}

dependencies {

    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.fragment.ktx)
    implementation(libs.androidx.appcompat)

    implementation(project(":core:database"))
    implementation(libs.immutable)
}
