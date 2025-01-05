plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.kotlin.compose)
    alias(libs.plugins.jetbrainsKotlinSerialization)
    alias(libs.plugins.google.services)
}

android {
    namespace = "com.example.fieldwise"
    compileSdk = 35

    defaultConfig {
        applicationId = "com.example.fieldwise"
        minSdk = 34
        targetSdk = 35
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
    kotlinOptions {
        jvmTarget = "11"
    }
    buildFeatures {
        compose = true
    }
    testOptions {
        unitTests {
            isReturnDefaultValues = true
        }
    }
}

dependencies {

    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.lifecycle.runtime.ktx)
    implementation(libs.androidx.activity.compose)
    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.androidx.ui)
    implementation(libs.androidx.ui.graphics)
    implementation(libs.androidx.ui.tooling.preview)
    implementation(libs.androidx.material3)

    implementation(libs.androidx.navigation.compose)
    implementation(libs.kotlinx.serialization.json)
    implementation("androidx.navigation:navigation-compose:2.7.1")
    implementation("androidx.compose.ui:ui:1.5.1")
    implementation("androidx.compose.ui:ui-text:1.5.1")
    implementation("androidx.compose.material:material:1.5.1")
    implementation("androidx.compose.ui:ui-tooling-preview:1.5.1")
    implementation("androidx.constraintlayout:constraintlayout:2.1.4")
    implementation("androidx.navigation:navigation-fragment-ktx:2.6.0")
    implementation("androidx.navigation:navigation-ui-ktx:2.6.0")
    implementation("io.coil-kt:coil-compose:2.4.0")

    // Firebase
    implementation(platform(libs.firebase.bom))
    implementation(libs.firebase.analytics)
    implementation(libs.firebase.auth)
    implementation(libs.firebase.firestore)
    implementation(libs.firebase.database)
    implementation(libs.firebase.database.ktx)
    implementation(libs.androidx.tv.material)
    implementation(libs.firebase.storage)

    androidTestImplementation(libs.androidx.espresso.core)
    androidTestImplementation(platform(libs.androidx.compose.bom))
    androidTestImplementation(libs.androidx.ui.test.junit4)
    debugImplementation(libs.androidx.ui.tooling)
    debugImplementation(libs.androidx.ui.test.manifest)

    // HTTP requests (KTor)
    implementation("io.ktor:ktor-client-core:2.0.0")
    implementation("io.ktor:ktor-client-cio:2.0.0")
    implementation("io.ktor:ktor-client-content-negotiation:2.0.0")
    implementation("io.ktor:ktor-serialization-kotlinx-json:2.0.0")
    implementation("org.jetbrains.kotlinx:kotlinx-serialization-json:1.3.2")
    implementation("io.ktor:ktor-client-logging:2.0.0")

    // MockK
    testImplementation("io.mockk:mockk:1.13.14")

    // Coroutines Test
    testImplementation("org.jetbrains.kotlinx:kotlinx-coroutines-test:1.7.3")

    // Ktor HTTP Mock
    testImplementation("io.ktor:ktor-client-mock:2.0.0")

    // Junit 5
    testImplementation("org.junit.jupiter:junit-jupiter-api:5.10.0")
    testRuntimeOnly("org.junit.jupiter:junit-jupiter-engine:5.10.0")

    //  AndroidX Test
    testImplementation("androidx.arch.core:core-testing:2.2.0")

    androidTestImplementation("androidx.test.ext:junit:1.1.3")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.6.1")
    androidTestImplementation("androidx.arch.core:core-testing:2.2.0")
    testImplementation("org.slf4j:slf4j-simple:2.0.9")
}

tasks.withType<Test> {
    useJUnitPlatform()
}