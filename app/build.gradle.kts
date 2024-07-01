plugins {
    alias(libs.plugins.androidApplication)
    alias(libs.plugins.jetbrainsKotlinAndroid)
    id("com.google.gms.google-services")
    //id("org.jetbrains.kotlin.android")
    //id("org.jetbrains.kotlin.android")
    //id("org.jetbrains.kotlin.android")
}

android {
    namespace = "com.example.orderfood"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.example.orderfood"
        minSdk = 24
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
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
    viewBinding {
        enable =  true
    }
}

dependencies {

    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.appcompat)
    implementation(libs.material)
    implementation(libs.androidx.activity)
    implementation(libs.androidx.constraintlayout)
    implementation("com.google.firebase:firebase-messaging:24.0.0")

    implementation(files("C:/Users/ADMIN/Downloads/zpdk-release-v3.1.aar"))
    implementation("androidx.appcompat:appcompat:1.6.1")
    implementation("com.google.android.material:material:1.10.0")
    implementation("androidx.activity:activity:1.8.0")
    implementation("androidx.constraintlayout:constraintlayout:2.1.4")

    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)

    implementation ("com.squareup.retrofit2:retrofit:2.9.0")
    implementation ("com.google.code.gson:gson:2.10.1")
    implementation ("com.squareup.retrofit2:converter-gson:2.9.0")
    implementation ("io.reactivex.rxjava2:rxkotlin:2.3.0")
    implementation ("io.reactivex.rxjava2:rxandroid:2.0.2")

    implementation ("androidx.navigation:navigation-fragment-ktx:2.7.7")
    implementation ("androidx.navigation:navigation-ui-ktx:2.7.7")
    implementation ("com.github.bumptech.glide:glide:4.16.0")
    implementation ("io.github.youth5201314:banner:2.2.3")
    implementation ("com.squareup.retrofit2:adapter-rxjava2:2.4.0")
    implementation ("androidx.lifecycle:lifecycle-livedata:2.4.0")

    implementation ("io.github.cymchad:BaseRecyclerViewAdapterHelper4:4.1.4")
    implementation ("com.squareup.okhttp3:logging-interceptor:4.9.0")
    implementation ("io.github.youth5201314:banner:2.2.3")
    implementation ("me.relex:circleindicator:2.1.6")
    implementation ("de.hdodenhof:circleimageview:3.1.0")

    implementation("io.reactivex.rxjava2:rxjava:2.1.9")
//    implementation fileTree(include: ['*.jar', '*.aar'], dir: 'libs')
}