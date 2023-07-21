@file:Suppress("UNUSED_VARIABLE")

@Suppress("DSL_SCOPE_VIOLATION")  // TODO: Remove once KTIJ-19369 is fixed
plugins {
    alias(libs.plugins.android.library)
    alias(libs.plugins.kotlin.multiplatform)
    alias(libs.plugins.kotlin.cocoapods)
}

val javaVersionValue = rootProject.extra["javaVersion"] as JavaVersion

@OptIn(org.jetbrains.kotlin.gradle.ExperimentalKotlinGradlePluginApi::class)
kotlin {
    jvmToolchain {
        languageVersion.set(JavaLanguageVersion.of(javaVersionValue.getInt()))
    }

    targetHierarchy.default()

    android {
        publishAllLibraryVariants()

        compilations.all {
            kotlinOptions {
                jvmTarget = javaVersionValue.majorVersion
            }
        }
    }

    iosX64()
    iosArm64()
    iosSimulatorArm64()

    watchosX64()
    watchosArm64()
    watchosSimulatorArm64()

    tvosX64()
    tvosArm64()
    tvosSimulatorArm64()

    cocoapods {
        summary = "Some description for the Shared Module"
        homepage = "Link to the Shared Module homepage"
        version = rootProject.extra["versionName"] as String

        ios.deploymentTarget = libs.versions.ios.deployment.target.get()
        watchos.deploymentTarget = libs.versions.ios.deployment.target.get()
        tvos.deploymentTarget = libs.versions.ios.deployment.target.get()

        framework {
            baseName = "shared"
        }
    }

    sourceSets {
        val commonMain by getting {
            dependencies {
                implementation(libs.kotlinx.coroutines.core)
                implementation(libs.kotlinx.datetime.core)

                implementation(kotlin("reflect"))
            }
        }

        val androidMain by getting {
            dependsOn(commonMain)
        }

        val commonTest by getting {
            dependsOn(commonMain)

            dependencies {
                implementation(kotlin("test"))
            }
        }

        val androidUnitTest by getting {
            dependsOn(androidMain)
            dependsOn(commonTest)
        }

        val androidInstrumentedTest by getting {
            dependsOn(androidMain)
            dependsOn(commonTest)
        }

        val appleMain by getting {
            dependsOn(commonMain)
        }

        val appleTest by getting {
            dependsOn(appleMain)
            dependsOn(commonTest)
        }

        val iosX64Main by getting {
            dependencies {
            }
        }

        val iosArm64Main by getting {
            dependencies {
            }
        }

        val iosSimulatorArm64Main by getting {
            dependencies {
            }
        }

        val iosMain by getting {
            dependsOn(appleMain)

            iosX64Main.dependsOn(this)
            iosArm64Main.dependsOn(this)
            iosSimulatorArm64Main.dependsOn(this)

            dependencies {
            }
        }

        val iosX64Test by getting {
            dependsOn(iosX64Main)
        }

        val iosArm64Test by getting {
            dependsOn(iosArm64Main)
        }

        val iosSimulatorArm64Test by getting {
            dependsOn(iosSimulatorArm64Main)
        }

        val iosTest by getting {
            dependsOn(appleTest)

            iosX64Test.dependsOn(this)
            iosArm64Test.dependsOn(this)
            iosSimulatorArm64Test.dependsOn(this)
        }

        val watchosX64Main by getting
        val watchosArm64Main by getting
        val watchosSimulatorArm64Main by getting
        val watchosMain by getting {
            dependsOn(appleMain)

            watchosX64Main.dependsOn(this)
            watchosArm64Main.dependsOn(this)
            watchosSimulatorArm64Main.dependsOn(this)
        }

        val watchosX64Test by getting {
            dependsOn(watchosX64Main)
        }

        val watchosArm64Test by getting {
            dependsOn(watchosArm64Main)
        }

        val watchosSimulatorArm64Test by getting {
            dependsOn(watchosSimulatorArm64Main)
        }

        val watchosTest by getting {
            dependsOn(appleTest)

            watchosX64Test.dependsOn(this)
            watchosArm64Test.dependsOn(this)
            watchosSimulatorArm64Test.dependsOn(this)
        }

        val tvosX64Main by getting {
            dependencies {
            }
        }

        val tvosArm64Main by getting {
            dependencies {
            }
        }

        val tvosSimulatorArm64Main by getting {
            dependencies {
            }
        }

        val tvosMain by getting {
            dependsOn(appleMain)

            tvosX64Main.dependsOn(this)
            tvosArm64Main.dependsOn(this)
            tvosSimulatorArm64Main.dependsOn(this)
        }

        val tvosX64Test by getting {
            dependsOn(tvosX64Main)
        }

        val tvosArm64Test by getting {
            dependsOn(tvosArm64Main)
        }

        val tvosSimulatorArm64Test by getting {
            dependsOn(tvosSimulatorArm64Main)
        }

        val tvosTest by getting {
            dependsOn(appleTest)

            tvosX64Test.dependsOn(this)
            tvosArm64Test.dependsOn(this)
            tvosSimulatorArm64Test.dependsOn(this)
        }
    }
}

fun JavaVersion.getInt(): Int {
    return this.ordinal + 1
}

android {
    val minSdkValue = rootProject.extra["minSdk"] as Int
    val targetSdkValue = rootProject.extra["targetSdk"] as Int

    namespace = rootProject.extra["libraryId"] as String
    compileSdk = targetSdkValue

    defaultConfig {
        minSdk = minSdkValue

        consumerProguardFiles("consumer-rules.pro")
    }

    buildFeatures {
        compose = false
    }

    packaging {
        resources {
            pickFirsts += "**/*.proto"
            excludes += "/META-INF/*"
        }
    }

    buildTypes {
        release {
            isMinifyEnabled = false

            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }

        debug {
            isMinifyEnabled = false

            matchingFallbacks += listOf("release")
        }
    }

    compileOptions {
        sourceCompatibility = javaVersionValue
        targetCompatibility = javaVersionValue
    }

    dependencies {
        implementation(libs.androidx.collection.ktx)

        implementation(libs.kotlinx.coroutines.android)

        testImplementation(libs.junit)
        androidTestImplementation(libs.bundles.androidx.test.espresso)

    }
}