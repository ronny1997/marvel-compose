pluginManagement {
    repositories {
        gradlePluginPortal()
        google()
        mavenCentral()
    }
}
dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        google()
        mavenCentral()
    }
    versionCatalogs {
        create("libs") {
            from(files("gradle/lib.versions.toml"))
        }
    }
}
rootProject.name = "MarvelCompose"
include(":app")
include(":domain")
include(":data")
include(":ui")
include(":framework")
