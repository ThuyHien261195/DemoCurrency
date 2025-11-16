plugins {
    alias(libs.plugins.android.library)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.ksp)
}

//apply(from = rootProject.file("gradle/kotlin_module_config.gradle"))
apply(from = rootProject.file("gradle/android_library_config.gradle"))

android {
    namespace = "com.example.core.database"
}

dependencies {

    implementation(libs.roomRuntime)
    implementation(libs.roomKtx)
    ksp(libs.roomCompiler)

    implementation(libs.hilt.android)
    ksp(libs.hilt.compiler)
//    implementation(libs.androidx.core.ktx)
//    implementation(libs.androidx.appcompat)
//    implementation(libs.material)
//    testImplementation(libs.junit)
//    androidTestImplementation(libs.androidx.junit)
//    androidTestImplementation(libs.androidx.espresso.core)
}