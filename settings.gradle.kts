pluginManagement {
    repositories {
        google {
            content {
                includeGroupByRegex("com\\.android.*")
                includeGroupByRegex("com\\.google.*")
                includeGroupByRegex("androidx.*")
            }
        }
        mavenCentral()
        gradlePluginPortal()
    }
}
dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        google()
        mavenCentral()
    }
}

rootProject.name = "GameMeter"

include(":androidApp")
include(":composeApp")
include(":core:domain")
include(":core:data")
include(":core:presentation")
include(":core:ui")
include(":feature:games:domain")
include(":feature:games:api")
include(":feature:games:data")
include(":feature:games:presentation")
