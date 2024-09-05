import java.util.*

plugins {
    id("java")
    id("maven-publish")
    id("signing")
}

val mainProjectId = "koralix-commons"
val mainProjectName = "Koralix Commons"
val javaVersion: String by extra
val apiVersion: String by extra

subprojects {
    plugins.apply("java")
    plugins.apply("maven-publish")
    plugins.apply("signing")

    group = "com.koralix.commons"

    // "koralix-commons" if the project is commons, "koralix-commons-<module>" if the project is a module
    val projectId = if (name == "commons") mainProjectId else "${mainProjectId}-${name}"
    // "Koralix Commons" if the project is commons, "Koralix Commons <Module>" if the project is a module
    val projectName = if (name == "commons") "Koralix Commons" else "Koralix Commons ${name.replaceFirstChar {
        if (it.isLowerCase()) it.titlecase(
            Locale.getDefault()
        ) else it.toString()
    }}"

    logger.lifecycle("Configuring project $projectName ($projectId)")

    java {
        withJavadocJar()
        withSourcesJar()

        toolchain {
            languageVersion = JavaLanguageVersion.of(javaVersion)
        }
    }

    tasks.named<Javadoc>("javadoc") {
        title = "$projectName - $version"
        options {
            overview = file("${rootDir}/javadoc/overview.html").absolutePath
            windowTitle = "${projectId}:${project.version}"
            header = file("${rootDir}/javadoc/header.html").readText()
                .replace("@version@", project.version as String)
                .replace("@projectId@", projectId)
                .replace("@projectName@", projectName)
            locale = "en"
        }
    }

    tasks.named<Test>("test") {
        useJUnitPlatform()
    }

    // Task for conditional publishing
    tasks.register("publishToOssrh") {
        group = "publishing"
        description = "Publishes the project to OSSRH"
        tasks.withType<PublishToMavenRepository>()
            .matching { it.repository.name.startsWith("ossrh") }
            .forEach {
            dependsOn(it)
        }
    }

    gradle.taskGraph.whenReady {
        tasks.withType<PublishToMavenRepository>().configureEach {
            val ossrh = if (project.version.toString().endsWith("SNAPSHOT")) "ossrh-snapshots" else "ossrh"
            val enabled = !repository.name.startsWith("ossrh") || repository.name == ossrh
            onlyIf {
                enabled
            }
        }
        logger.lifecycle("Configured conditional publishing: ${tasks.withType<PublishToMavenRepository>()
            .matching { it.onlyIf.isSatisfiedBy(it) }
            .joinToString { it.name }}")
    }

    publishing {
        publications {
            create<MavenPublication>(projectName.replace(" ", "")) {
                from(components["java"])

                pom {
                    name.set("${group}:${projectId}")
                    description.set("Koralix Commons provides common data structures, algorithms and architectures for our other software solutions.")
                    url.set("https://github.com/Koralix-Studios/${mainProjectId}")

                    licenses {
                        license {
                            name.set("The Apache License, Version 2.0")
                            url.set("https://github.com/Koralix-Studios/${mainProjectId}/blob/master/LICENSE")
                        }
                    }

                    developers {
                        developer {
                            name.set("JohanVonElectrum")
                            email.set("johanvonelectrum@gmail.com")
                            organization.set("Koralix Studios")
                            organizationUrl.set("https://www.koralix.com")
                        }
                    }

                    scm {
                        connection.set("scm:git:git://github.com/koralix-studios/${mainProjectId}.git")
                        developerConnection.set("scm:git:ssh://github.com/koralix-studios/${mainProjectId}.git")
                        url.set("https://github.com/Koralix-Studios/${mainProjectId}/tree/master")
                    }
                }
            }
        }

        repositories {
            maven {
                name = "GitHubPackages"
                url = uri("https://maven.pkg.github.com/koralix-studios/koralix-commons")
                credentials {
                    username = System.getenv("GITHUB_ACTOR") ?: project.findProperty("gpr.user") as String?
                    password = System.getenv("GITHUB_TOKEN") ?: project.findProperty("gpr.key") as String?
                }
            }
            maven {
                name = "ossrh"
                url = uri("https://s01.oss.sonatype.org/service/local/staging/deploy/maven2/")
                credentials {
                    username = System.getenv("OSSRH_USERNAME") ?: project.findProperty("ossrh.user") as String?
                    password = System.getenv("OSSRH_PASSWORD") ?: project.findProperty("ossrh.password") as String?
                }
            }
            maven {
                name = "ossrh-snapshots"
                url = uri("https://s01.oss.sonatype.org/content/repositories/snapshots")
                credentials {
                    username = System.getenv("OSSRH_USERNAME") ?: project.findProperty("ossrh.user") as String?
                    password = System.getenv("OSSRH_PASSWORD") ?: project.findProperty("ossrh.password") as String?
                }
            }
        }
    }

    signing {
        if (
            System.getenv("SIGNING_KEY") != null &&
            System.getenv("SIGNING_PASSWORD") != null
        ) {
            useInMemoryPgpKeys(System.getenv("SIGNING_KEY"), System.getenv("SIGNING_PASSWORD"))
        } else if (
            project.findProperty("signing.keyId") == null ||
            project.findProperty("signing.password") == null ||
            project.findProperty("signing.secretKeyRingFile") == null
        ) {
            logger.warn("Signing key is not configured and not available in env")
            return@signing
        } else {
            useGpgCmd()
        }

        logger.lifecycle("Signing key is configured")

        publishing.publications.forEach {
            sign(it)
        }
    }
}

tasks.named<Javadoc>("javadoc") {
    group = "documentation"
    description = "Generates Javadoc for all projects"
    title = "$mainProjectName - $apiVersion"

    source(project.subprojects.flatMap {
        it.sourceSets.getByName("main").allJava
    })
    classpath = files(project.subprojects.flatMap {
        it.sourceSets.getByName("main").compileClasspath
    })
    val targetDir = file("${project.layout.buildDirectory.asFile.get()}/docs/javadoc")
    setDestinationDir(targetDir)
    options {
        overview = file("${rootDir}/javadoc/overview.html").absolutePath
        windowTitle = "${mainProjectId}:${project.version}"
        header = file("${rootDir}/javadoc/header.html").readText()
            .replace("@version@", project.version as String)
            .replace("@projectId@", mainProjectId)
            .replace("@projectName@", mainProjectName)
        locale = "en"
    }

    doNotTrackState("javadoc")
    outputs.dir(targetDir)
}
