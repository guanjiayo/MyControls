@file:Suppress("UnstableApiUsage")

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
        maven { url = uri("https://www.jitpack.io") }
    }
}
dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        google()
        mavenCentral()
        maven { url = uri("https://www.jitpack.io") }
    }
}

rootProject.name = "MyControls"
include(":app")
include(":progressbar")
include(":timer")
include(":rvgather")
include(":baseadapter-recyclerview")
include(":dialoglib")
include(":customcontrols")
include(":gridlayout")
include(":widget_sample")
include(":expand_edittext")
include(":lib_DropDownMenu")
include(":lib_indexLayout")

