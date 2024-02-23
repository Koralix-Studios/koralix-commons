plugins {
    id("java")
    id("idea")

    id("org.spongepowered.gradle.vanilla") version "0.2.1-SNAPSHOT"
}

val mavenGroup: String by extra
val apiVersion: String by extra
val javaVersion: String by extra
val jUnitVersion: String by extra

val minecraftVersion: String by extra
val modId: String by extra

group = mavenGroup
version = apiVersion

base {
    archivesName.set("${modId}-${minecraftVersion}-${version}")
}

minecraft {
    version(minecraftVersion)
    if(file("src/main/resources/${modId}.accesswidener").exists()){
        accessWideners(file("src/main/resources/${modId}.accesswidener"))
    }
}

repositories {
    mavenCentral()
}

dependencies {
    implementation(project(":commons"))
    implementation(project(":concurrent"))

    compileOnly(
        group = "org.spongepowered",
        name = "mixin",
        version = "0.8.5"
    )
    compileOnly("com.google.auto.service:auto-service-annotations:1.1.1")
    annotationProcessor("com.google.auto.service:auto-service:1.1.1")

    testImplementation(platform("org.junit:junit-bom:${jUnitVersion}"))
    testImplementation("org.junit.jupiter:junit-jupiter")
}

tasks.named<Test>("test") {
    useJUnitPlatform()
}
