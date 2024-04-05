import io.ktor.plugin.features.*

val ktor_version: String by project
val kotlin_css_version: String by project
val logback_version: String by project
val exposedVersion: String by project
val postgresVersion: String by project

plugins {
    kotlin("jvm") version "1.9.22"
    kotlin("plugin.serialization") version "1.9.22"
    id("org.graalvm.buildtools.native") version "0.10.1"
    id("io.ktor.plugin") version "2.3.8"
}

group = "dev.kon3gor"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
    // Ktor
    implementation("io.ktor:ktor-server-core-jvm:$ktor_version")
    implementation("io.ktor:ktor-server-netty-jvm:$ktor_version")
    implementation("io.ktor:ktor-server-html-builder:$ktor_version")
    implementation("io.ktor:ktor-server-cors:$ktor_version")
    implementation("io.ktor:ktor-server-content-negotiation:$ktor_version")
    implementation("io.ktor:ktor-serialization-kotlinx-json:$ktor_version")
    implementation("io.ktor:ktor-server-status-pages:$ktor_version")

    // Exposed
    implementation("org.jetbrains.exposed:exposed-core:$exposedVersion")
    implementation("org.jetbrains.exposed:exposed-jdbc:$exposedVersion")
    implementation("org.jetbrains.exposed:exposed-java-time:$exposedVersion")

    // Postgres
    implementation("org.postgresql:postgresql:$postgresVersion")

    // Other shit
    implementation("org.jetbrains.kotlin-wrappers:kotlin-css:$kotlin_css_version")
    implementation("ch.qos.logback:logback-classic:$logback_version")


    testImplementation("org.jetbrains.kotlin:kotlin-test")
}

tasks.test {
    useJUnitPlatform()
}

kotlin {
    jvmToolchain(21)
}

application {
    mainClass.set("io.ktor.server.netty.EngineMain")
    // applicationDefaultJvmArgs += "-Djava.library.path=/Users/kon3gor/Documents/development/backend/WebLists/src/main/native/bin"
}

ktor {
    docker {
        portMappings.set(listOf(
            DockerPortMapping(
                outsideDocker = 80,
                insideDocker = 8080,
            )
        ))

        jreVersion.set(JavaVersion.VERSION_21)

        externalRegistry.set(
            DockerImageRegistry.externalRegistry(
                username = providers.environmentVariable("DOCKER_HUB_USERNAME"),
                password = providers.environmentVariable("DOCKER_HUB_PASSWORD"),
                project = provider { "lists-app" },
                hostname = provider { "cr.yandex" },
                namespace = provider { "crp59r7snaughmoe7iqk" },
            )
        )
    }
}