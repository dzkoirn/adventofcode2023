
buildscript {
    repositories {
        mavenCentral()
        gradlePluginPortal()
    }
}

repositories {
    mavenCentral()
}

plugins {
    kotlin("jvm") version "1.9.20"
    application
}

java {
    toolchain {
        languageVersion.set(JavaLanguageVersion.of(21))
    }
}

kotlin {
    jvmToolchain(21)
}

tasks.test { // See 5️⃣
    useJUnitPlatform() // JUnitPlatform for tests. See 6️⃣
    testLogging {
        events("passed", "skipped", "failed")
    }
}

dependencies {
    implementation("org.json:json:20230227")

    testImplementation(platform("org.junit:junit-bom:5.9.0"))
    testImplementation("org.junit.jupiter:junit-jupiter")
    testImplementation("org.junit.jupiter:junit-jupiter-params:5.9.0")
}