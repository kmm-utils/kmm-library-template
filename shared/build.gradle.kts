@file:Suppress("UNUSED_VARIABLE")

import org.jetbrains.kotlin.gradle.dsl.JsModuleKind.MODULE_COMMONJS
import org.jetbrains.kotlin.gradle.dsl.JsModuleKind.MODULE_UMD
import org.jetbrains.kotlin.gradle.targets.js.dsl.ExperimentalWasmDsl

@Suppress("DSL_SCOPE_VIOLATION")  // TODO: Remove once KTIJ-19369 is fixed
plugins {
    alias(libs.plugins.android.library)
    alias(libs.plugins.kotlin.multiplatform)
    alias(libs.plugins.kotlin.cocoapods)
    id("maven-publish")
}

val javaVersionValue = rootProject.extra["javaVersion"] as JavaVersion
val moduleNameValue = rootProject.extra["moduleName"] as String
val outputJsFilenameValue = rootProject.extra["outputJsFilename"] as String

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

    js(IR) {
        moduleName = moduleNameValue

        compilations["main"].packageJson {
            customField("hello", mapOf("one" to 1, "two" to 2))
        }

        generateTypeScriptDefinitions()

        browser {
            commonWebpackConfig {
                cssSupport {
                    enabled.set(true)
                }
            }

            webpackTask {
                outputFileName = outputJsFilenameValue
                output.libraryTarget = "commonjs2"
            }

            testTask {
                enabled = false
                useKarma {
                    useIe()
                    useSafari()
                    useFirefox()
                    useChrome()
                    useChromeCanary()
                    useChromeHeadless()
                    usePhantomJS()
                    useOpera()
                }
            }
        }

        nodejs {
            useCommonJs()
        }
    }

    jvm {
        compilations.all {
            kotlinOptions {
                jvmTarget = javaVersionValue.majorVersion
            }
        }
    }

    @OptIn(ExperimentalWasmDsl::class)
    wasm {
        d8()
    }

    // Windows cross-compiling target
    mingwX64 {
        binaries {
            sharedLib {
                baseName = "lib$moduleNameValue"
            }
        }
    }

    linuxX64 {
        binaries {
            sharedLib {
                baseName = moduleNameValue
            }
        }
    }

    linuxArm64 {
        binaries {
            sharedLib {
                baseName = moduleNameValue
            }
        }
    }

    macosX64 {
        binaries {
            sharedLib {
                baseName = moduleNameValue
            }
        }
    }

    macosArm64 {
        binaries {
            sharedLib {
                baseName = moduleNameValue
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

    targets {
        js {
            browser {
                compilations.all {
                    compilerOptions.configure {
                        moduleKind.set(MODULE_UMD)
                    }
                }
            }

            nodejs {
                compilations.all {
                    compilerOptions.configure {
                        moduleKind.set(MODULE_COMMONJS)
                    }
                }
            }

            compilations.all {
                compilerOptions.configure {
                    sourceMap.set(true)
                }
            }

            binaries.executable()
        }
    }

    val publicationsFromMainHost = listOf(jvm(), js(IR)).map { it.name } + "kotlinMultiplatform"

    publishing {
        publications {
            matching { it.name in publicationsFromMainHost }.all {
                val targetPublication = this@all
                tasks.withType<AbstractPublishToMaven>()
                    .matching { it.publication == targetPublication }
                    .configureEach { onlyIf { findProperty("isMainHost") == "true" } }
            }
        }
    }

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

                implementation(kotlin("reflect"))
            }
        }

        val jsMain by getting {
            dependsOn(commonMain)

            dependencies {
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

        val jsTest by getting {
            dependsOn(jsMain)
            dependsOn(commonTest)

            dependencies {
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

        val linuxX64Main by getting {
            dependencies {
            }
        }

        val linuxArm64Main by getting {
            dependencies {
            }
        }

        val linuxMain by getting {
            dependsOn(commonMain)

            linuxX64Main.dependsOn(this)
            linuxArm64Main.dependsOn(this)

            dependencies {
            }
        }

        val linuxX64Test by getting {
            dependencies {
                dependsOn(linuxX64Main)
            }
        }

        val linuxArm64Test by getting {
            dependencies {
                dependsOn(linuxArm64Main)
            }
        }

        val linuxTest by getting {
            dependsOn(linuxMain)
            dependsOn(commonTest)

            linuxX64Test.dependsOn(this)
            linuxArm64Test.dependsOn(this)

            dependencies {
            }
        }

        val mingwX64Main by getting {
            dependencies {
            }
        }

        val mingwMain by getting {
            dependsOn(commonMain)

            mingwX64Main.dependsOn(this)

            dependencies {
            }
        }

        val mingwX64Test by getting {
            dependencies {
                dependsOn(mingwX64Main)
            }
        }

        val mingwTest by getting {
            dependsOn(mingwMain)
            dependsOn(commonTest)

            mingwX64Test.dependsOn(this)

            dependencies {
            }
        }

        val appleMain by getting {
            dependsOn(commonMain)

            dependencies {
            }
        }

        val appleTest by getting {
            dependsOn(appleMain)
            dependsOn(commonTest)

            dependencies {
            }
        }

        val macosX64Main by getting {
            dependencies {
            }
        }

        val macosArm64Main by getting {
            dependencies {
            }
        }

        val macosMain by getting {
            dependsOn(appleMain)

            macosX64Main.dependsOn(this)
            macosArm64Main.dependsOn(this)

            dependencies {
            }
        }

        val macosX64Test by getting {
            dependencies {
                dependsOn(macosX64Main)
            }
        }

        val macosArm64Test by getting {
            dependencies {
                dependsOn(macosArm64Main)
            }
        }

        val macosTest by getting {
            dependsOn(appleTest)

            macosX64Test.dependsOn(this)
            macosArm64Test.dependsOn(this)

            dependencies {
            }
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

tasks.withType<Wrapper> {
    gradleVersion = "8.0"
    distributionType = Wrapper.DistributionType.ALL
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