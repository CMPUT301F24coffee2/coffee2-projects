configurations {
    all {
//        exclude(group = "org.jetbrains.kotlin", module = "kotlin-stdlib")
//        exclude(group = "org.jetbrains.kotlin", module = "kotlin-coroutines-core")
//        exclude(group = "org.jetbrains.kotlin", module = "kotlin-stdlib-jdk8")
    }
    configurations.all {
        resolutionStrategy {
            force("com.google.firebase:firebase-common:20.4.2")
            force("com.google.android.gms:play-services-basement:18.4.0")
            force("com.google.android.gms:play-services-tasks:18.2.0")
            force("com.google.firebase:firebase-annotations:16.2.0")
        }
    }
}

plugins {
    alias(libs.plugins.android.application)
    id("com.google.gms.google-services")
}
tasks.withType<Test>{
    useJUnitPlatform()
}

android {
    namespace = "com.example.coffee2_app"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.example.coffee2_app"
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
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
    buildFeatures {
        viewBinding = true
    }
}

dependencies {
    implementation("org.jetbrains.kotlin:kotlin-stdlib:1.8.0")
    //check later for the implementation of firebase
    implementation ("androidx.recyclerview:recyclerview:1.2.1")
    implementation("com.github.bumptech.glide:glide:4.12.0")
    implementation(platform("com.google.firebase:firebase-bom:32.7.1"))
    implementation("com.google.firebase:firebase-firestore")
    implementation("com.google.firebase:firebase-storage")
    implementation("com.google.android.gms:play-services-location:21.3.0")
    implementation("com.google.firebase:firebase-auth") {
        exclude(group = "com.google.firebase", module = "firebase-common")
    }


    implementation(libs.appcompat)
    implementation(libs.material)
    implementation(libs.constraintlayout)
    implementation(libs.lifecycle.livedata.ktx)
    implementation(libs.lifecycle.viewmodel.ktx)
    implementation(libs.navigation.fragment)
    implementation(libs.navigation.ui)
    implementation(libs.espresso.intents)
    //implementation(libs.firebase.storage)
    testImplementation(libs.junit)
    testImplementation(libs.androidx.junit)
    testImplementation(libs.espresso.core)
    androidTestImplementation(libs.ext.junit)
    testImplementation("org.junit.jupiter:junit-jupiter-api:5.0.1")
    testRuntimeOnly("org.junit.jupiter:junit-jupiter-engine:5.0.1")
    androidTestImplementation(libs.espresso.core)
    implementation("com.google.zxing:core:3.3.2")
    implementation("com.journeyapps:zxing-android-embedded:4.3.0")
    testImplementation("org.mockito:mockito-core:3.12.4")
    testImplementation("org.mockito:mockito-inline:3.12.4")
    testImplementation("net.bytebuddy:byte-buddy:1.12.20")

    implementation("com.github.dhaval2404:imagepicker:2.1")
}