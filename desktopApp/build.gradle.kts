import org.jetbrains.compose.desktop.application.dsl.TargetFormat

plugins {
    kotlin("multiplatform")
    id("org.jetbrains.compose")
    id("dev.hydraulic.conveyor") version "1.9"
}

version = 1
group = "TV-Multiplatform"


kotlin {
    jvm()
    sourceSets {
        val jvmMain by getting {
            dependencies {
                implementation(compose.desktop.currentOs)
                implementation(project(":shared"))
            }
        }
    }
}

compose.desktop {
    application {
        mainClass = "com.corner.MainKt"

        buildTypes.release.proguard {
//            obfuscate.set(true)
            isEnabled.set(true)
            configurationFiles.from(project.file("rules.pro"))
        }

        jvmArgs("-Dfile.encoding=UTF-8")

        nativeDistributions {
            targetFormats(TargetFormat.Dmg, TargetFormat.Msi, TargetFormat.Deb, TargetFormat.Pkg)
            packageName = "TV"
            packageVersion = "1.1.11"
            vendor = "TV Multiplatform"

            modules(
                "java.compiler",
                "java.instrument",
                "java.management",
                "java.naming",
                "java.net.http",
                "java.rmi",
                "java.security.jgss",
                "java.sql",
                "jdk.httpserver",
                "jdk.unsupported"
            )
            appResourcesRootDir.set(project.layout.projectDirectory.dir("resources"))
//            app icons https://github.com/JetBrains/compose-multiplatform/tree/master/tutorials/Native_distributions_and_local_execution#app-icon
            windows {
                iconFile.set(project.file("src/jvmMain/resources/icon-s.ico"))
                dirChooser = true
                upgradeUuid = "161FA5A0-A30B-4568-9E84-B3CD637CC8FE"
            }

            linux {
                iconFile.set(project.file("src/jvmMain/resources/TV-icon-s.png"))

            }

            macOS {
                iconFile.set(project.file("src/jvmMain/resources/icon.icns"))
            }

        }

    }
}

project.configurations.create("desktopRuntimeClasspath") {
    extendsFrom(project.configurations.findByName("jvmRuntimeClasspath"))
}