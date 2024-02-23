pluginManagement {
    repositories {
        maven {
            name = "Fabric"
            url = uri("https://maven.fabricmc.net/")
        }
        maven {
            name = "Sponge Snapshots"
            url = uri("https://repo.spongepowered.org/repository/maven-public/")
        }
        gradlePluginPortal()
    }
}

rootProject.name = "koralix-commons"
include("commons")
include("concurrent")
include("minecraft")
