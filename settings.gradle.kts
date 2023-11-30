rootProject.name = "adventofcode-2023"

plugins {
    id("org.gradle.toolchains.foojay-resolver-convention") version("0.7.0")
}

dependencyResolutionManagement {
    versionCatalogs {
        create("versions") {
            version("kotlin", "1.9.20")
        }
    }
}