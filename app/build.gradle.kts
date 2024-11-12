import java.io.File

plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.google.dagger.hilt)
    alias(libs.plugins.google.ksp)
    alias(libs.plugins.arturbosch.detekt)
    alias(libs.plugins.jetbrains.kotlin.serialization)
    alias(libs.plugins.compose.compiler)
    alias(libs.plugins.jetbrains.kotlin.android)
    alias(libs.plugins.androidx.junit)

    kotlin("kapt")
}

android {
    namespace = "com.cryptoemergency.cryptoemergency"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.testwork.geoapp"
        minSdk = 26
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"

        resourceConfigurations += listOf("en_US", "ru_RU")

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        vectorDrawables {
            useSupportLibrary = true
        }
    }

    signingConfigs {
        create("release") {
            storeFile = file(getKeystoreFile())
            storePassword = ""
            keyAlias = ""
            keyPassword = ""
        }
    }
    buildTypes {
        release {
            isMinifyEnabled = true
            signingConfig = signingConfigs.getByName("release")

            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro",
                "proguard-rules-without-logs.pro",
            )

            ndk { debugSymbolLevel = null }

            buildConfigField(
                "String",
                "PROTOCOL",
                "\"${project.properties["prod.server.protocol"]}\""
            )
            buildConfigField(
                "String",
                "HOST",
                "\"${project.properties["prod.server.host"]}\""
            )
            buildConfigField(
                "int",
                "PORT",
                project.properties["prod.server.port"].toString()
            )
        }

        debug {
            isDebuggable = true
            isJniDebuggable = true

            ndk { debugSymbolLevel = "FULL" }

            buildConfigField(
                "String",
                "PROTOCOL",
                "\"${project.properties["dev.server.protocol"]}\"",
            )
            buildConfigField(
                "String",
                "HOST",
                "\"${project.properties["dev.server.host"]}\"",
            )
            buildConfigField(
                "int",
                "PORT",
                project.properties["dev.server.port"].toString(),
            )
        }

        create("releaseWithLogs") {
            signingConfig = signingConfigs.getByName("release")
            isDebuggable = true

            buildConfigField(
                "String",
                "PROTOCOL",
                "\"${project.properties["prod.server.protocol"]}\"",
            )
            buildConfigField(
                "String",
                "HOST",
                "\"${project.properties["prod.server.host"]}\"",
            )
            buildConfigField(
                "int",
                "PORT",
                project.properties["prod.server.port"].toString(),
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
        buildConfig = true
    }
    composeOptions {
        kotlinCompilerExtensionVersion = "1.5.1"
    }
    packaging {
        resources {
            excludes += "/META-INF/INDEX.LIST"
        }
    }
}

dependencies {

    implementation(libs.osmdroid)
    implementation(libs.osmdroid.wms)

    implementation(libs.play.services.location)

    implementation(libs.androidx.datastore.preferences)
    implementation(libs.androidx.datastore)

    implementation(libs.androidx.hilt.navigation.compose)
    implementation(libs.hilt.android)
    kapt(libs.hilt.compiler) // 09.2024 - Hilt не поддерживает ksp

    implementation(libs.ktor.client.core)
    implementation(libs.ktor.client.okhttp)
    implementation(libs.ktor.client.serialization)
    implementation(libs.ktor.client.content.negotiation)
    implementation(libs.ktor.serialization.kotlinx.json)
    implementation(libs.ktor.client.logging)

    implementation(libs.slf4j.api)
    implementation(libs.logback.classic)

    implementation(libs.accompanist.permissions)

    implementation(libs.core.ktx)
    implementation(libs.kotlin.reflect)

    implementation(libs.androidx.navigation.compose)

    implementation(libs.androidx.foundation)
    implementation(libs.androidx.material3)

    implementation(libs.androidx.core.ktx)

    implementation(libs.androidx.activity.compose)
    implementation(platform(libs.androidx.compose.bom))

    implementation(libs.androidx.ui)
    implementation(libs.androidx.ui.graphics)
    implementation(libs.androidx.ui.tooling.preview)

    implementation(libs.androidx.lifecycle.viewmodel.ktx)
    implementation(libs.androidx.lifecycle.runtime.ktx)
    implementation(libs.androidx.appcompat)
    implementation(libs.androidx.compose.material)

    detektPlugins(libs.detekt.formatting)

    androidTestImplementation(libs.androidx.ui.test.android)
    androidTestImplementation(libs.junit.jupiter.api)
    testImplementation(libs.junit.jupiter.api)
    testRuntimeOnly(libs.junit.jupiter.engine)
}

junitPlatform {
    instrumentationTests.includeExtensions.set(true)
}

ksp {
    arg("room.schemaLocation", "$projectDir/repository/database/schemas")
}

kapt {
    correctErrorTypes = true
    useBuildCache = true
}

detekt {
    toolVersion = "1.23.3"
    config.setFrom(file("config/detekt/detekt.yml"))
    buildUponDefaultConfig = true
}

tasks.detekt {
    reports {
        html.required.set(true)
        md.required.set(true)
        xml.required.set(false)
        txt.required.set(false)
        sarif.required.set(false)
    }
}

private fun getKeystoreFile(): File {
    val keystoreFile = File(rootProject.projectDir, "keystore.jks")
    if (!keystoreFile.exists()) {
//        val keystoreContent = Base64.getDecoder().decode()
//
//        keystoreContent?.let {
//            keystoreFile.writeBytes(it)
//        }
    }

    return keystoreFile
}
