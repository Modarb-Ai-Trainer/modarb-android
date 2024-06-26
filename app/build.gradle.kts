plugins {
    id("com.android.application")
    id("org.jetbrains.kotlin.android")
}

android {
    namespace = "com.modarb.android"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.modarb.android"
        minSdk = 22
        targetSdk = 34
        versionCode = 4
        versionName = "2.0"
        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"), "proguard-rules.pro"
            )
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    kotlinOptions {
        jvmTarget = "1.8"
    }
    buildFeatures {
        viewBinding = true
        dataBinding = true
    }

}


dependencies {
    implementation("com.tbuonomo:dotsindicator:5.0")
    implementation("androidx.core:core-ktx:1.12.0")
    implementation("androidx.appcompat:appcompat:1.6.1")
    implementation("com.google.android.material:material:1.11.0")
    implementation("androidx.constraintlayout:constraintlayout:2.1.4")
    implementation("androidx.navigation:navigation-fragment-ktx:2.7.7")
    implementation("androidx.navigation:navigation-ui-ktx:2.7.7")

    // Retrofit
    implementation("com.squareup.retrofit2:retrofit:2.9.0")
    implementation("com.squareup.retrofit2:converter-gson:2.9.0")
    implementation("com.squareup.okhttp3:logging-interceptor:4.9.1")
    // Paging
    implementation("androidx.paging:paging-runtime:3.1.1")

    // LiveData and ViewModel
    implementation("androidx.lifecycle:lifecycle-livedata-ktx:2.7.0")
    implementation("androidx.lifecycle:lifecycle-viewmodel-ktx:2.7.0")
    implementation("com.google.android.gms:play-services-basement:18.3.0")
    // json helper
    implementation("com.google.code.gson:gson:2.10")
    // progress indicators
    implementation("com.tbuonomo:dotsindicator:5.0")
    implementation("androidx.legacy:legacy-support-v4:1.0.0")
    implementation("androidx.activity:activity:1.9.0")
    // Image fetch
    implementation("com.github.bumptech.glide:glide:4.16.0")
    //Circle progress bar
    implementation("com.mikhaellopez:circularprogressbar:3.1.0")
    implementation("androidx.webkit:webkit:1.11.0")
    // Pose correction
    implementation("com.google.mlkit:pose-detection:18.0.0-beta4")
    implementation("com.google.mlkit:pose-detection-accurate:18.0.0-beta4")
    // CameraX
    implementation("androidx.camera:camera-camera2:1.3.4")
    implementation("androidx.camera:camera-lifecycle:1.3.4")
    implementation("androidx.camera:camera-view:1.3.4")

    // On Device Machine Learnings
    implementation("com.google.guava:guava:32.1.2-jre")
    implementation("androidx.camera:camera-core:1.3.4")
    implementation("com.google.android.gms:play-services-vision-common:19.1.3")

    testImplementation("junit:junit:4.13.2")
    androidTestImplementation("androidx.test.ext:junit:1.1.5")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.5.1")
    implementation("com.github.vipulasri:timelineview:1.1.5")


}

//configurations {
//    // Resolves dependency conflict caused by some dependencies use
//    // com.google.guava:guava and com.google.guava:listenablefuture together.
//    all*.exclude group: 'com.google.guava', module: 'listenablefuture'
//}
