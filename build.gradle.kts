val javaVersion by extra(JavaVersion.toVersion(libs.versions.java.get()))

val minSdk by extra(libs.versions.min.sdk.stable.get().toInt())
val minSdkEdge by extra(libs.versions.min.sdk.edge.get().toInt())

val targetSdk by extra(libs.versions.target.sdk.stable.get().toInt())
val targetSdkEdge by extra(libs.versions.target.sdk.edge.get().toInt())

val versionCode by extra(1)
val versionName by extra("1.0.0")

var libraryId by extra("com.example.kmmwire.shared")

buildscript {
    repositories {
        google()
        gradlePluginPortal()
        mavenCentral()
        maven("https://oss.sonatype.org/content/repositories/snapshots")
        maven("https://s01.oss.sonatype.org/content/repositories/releases/")
    }

    dependencies {
        classpath(libs.gragle.android)
    }
}

@Suppress("DSL_SCOPE_VIOLATION") // TODO: Remove once KTIJ-19369 is fixed
plugins {
    //trick: for the same plugin versions in all sub-modules
    alias(libs.plugins.android.library).apply(false)
    alias(libs.plugins.kotlin.multiplatform).apply(false)
    alias(libs.plugins.kotlin.cocoapods).apply(false)
    alias(libs.plugins.kotlin.kapt).apply(false)
    alias(libs.plugins.kotlin.jvm).apply(false)

}

tasks.register("clean", Delete::class) {
    delete(rootProject.buildDir)
}
