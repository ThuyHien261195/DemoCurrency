plugins {
    alias(libs.plugins.android.library)
    alias(libs.plugins.ksp)
}

apply(from = rootProject.file("gradle/android_common_config.gradle"))

android {
    namespace = "com.example.core.database"
}

dependencies {

    implementation(libs.roomRuntime)
    implementation(libs.roomKtx)
    ksp(libs.roomCompiler)

    implementation(libs.hilt.android)
    ksp(libs.hilt.compiler)

    testImplementation(libs.test.core.ktx)
    testImplementation(libs.junit)
    testImplementation(libs.robolectric)
}