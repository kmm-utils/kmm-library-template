pluginManagement {
    repositories {
        google()
        gradlePluginPortal()
        mavenCentral()

        maven("https://oss.sonatype.org/content/repositories/snapshots")
        maven("https://s01.oss.sonatype.org/content/repositories/releases/")
    }
}

@Suppress("UnstableApiUsage")
dependencyResolutionManagement {
    file(".env").readLines().forEach {
        if (it.isNotEmpty() && !it.startsWith("#")) {
            val pos = it.indexOf("=")
            val key = it.substring(0, pos)
            val value = it.substring(pos + 1)

            if (System.getenv(key) == null) {
                System.setProperty(key, value)
            }
        }
    }

    repositories {
        google()
        mavenCentral()

        maven("https://oss.sonatype.org/content/repositories/snapshots")
        maven("https://s01.oss.sonatype.org/content/repositories/releases/")

        maven("https://maven.pkg.github.com/kmm-utils/version-catalog") {
            credentials {
                username = System.getenv("GITHUB_ACTOR")
                    ?: System.getenv("USERNAME") ?: System.getProperty("USERNAME")
                password = System.getenv("GITHUB_TOKEN")
                    ?: System.getenv("TOKEN") ?: System.getProperty("TOKEN")
            }
        }
    }
    versionCatalogs {
        create("libs") {
            from("kmm.utils:version-catalog:0.3")

            version("java", "17")
            version("min-sdk-stable", "28")
            version("min-sdk-edge", "31")
            version("target-sdk-stable", "33")
            version("target-sdk-edge", "34")
            version("ios-deployment-target", "15")
        }
    }
}

rootProject.name = "MyLibrary"
include(":shared")