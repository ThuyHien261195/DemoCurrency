pluginManagement {
    repositories {
        google()
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

rootProject.name = "DemoCurrency"
include(":app")
include(":core:database")
include(":feature:currencyInfo")
include(":mediator:currencyinfo")
include(":core:ktTestUtils")
