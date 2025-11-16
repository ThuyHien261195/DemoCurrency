plugins {
    alias(libs.plugins.android.library)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.ksp)
    alias(libs.plugins.hilt)
}

apply(from = rootProject.file("gradle/android_library_config.gradle"))

android {
    namespace = "com.example.feature.currencyinfo"
}

dependencies {

    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.fragment.ktx)
    implementation(libs.androidx.appcompat)

    implementation(libs.hilt.android)
    ksp(libs.hilt.compiler)

    implementation(project(":core:database"))
    implementation(libs.immutable)

//    implementation(platform(libs.compose.bom))
//    implementation(libs.compose.ui)
//    implementation(libs.compose.material3)
//    implementation(libs.compose.preview)
//    implementation(libs.activity.compose)
//    debugImplementation(libs.compose.tooling)
}
