plugins { id("com.android.application") }

android {
    namespace = "com.miller.tv.launcher"
    compileSdk = 36
    buildToolsVersion = "36.0.0"
    defaultConfig {
        applicationId = "com.miller.tv.launcher"
        minSdk = 30
        targetSdk = 34
        versionCode = 1
        versionName = "0.1.0-stage1"
    }
    buildFeatures { viewBinding = true }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }
}
kotlin { compilerOptions { jvmTarget.set(org.jetbrains.kotlin.gradle.dsl.JvmTarget.JVM_17) } }
dependencies { testImplementation("junit:junit:4.13.2") }
