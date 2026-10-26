// settings.gradle.kts - COMPLETE file for the Mobile-App-Lab project.
// The pluginManagement block tells Gradle WHERE to download plugins from.
// Without google() here, the Android Gradle Plugin (com.android.application) cannot be found.
pluginManagement {
    repositories {
        google {
            content {
                includeGroupByRegex("com\\.android.*")
                includeGroupByRegex("com\\.google.*")
                includeGroupByRegex("androidx.*")   // also covers the Safe Args plugin (androidx.navigation)
            }
        }
        mavenCentral()
        gradlePluginPortal()
    }
}

// Where the app's libraries (dependencies) are downloaded from
dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        google()
        mavenCentral()
    }
}

rootProject.name = "Mobile-App-Lab"
include(":app")