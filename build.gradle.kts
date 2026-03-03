plugins {
    kotlin("jvm") version "2.2.0"
}

val targetJavaVersion = 21
kotlin {
    jvmToolchain(targetJavaVersion)
}

allprojects {
    repositories {
        mavenCentral()
        maven("https://repo.papermc.io/repository/maven-public/")
    }

    dependencies {
        compileOnly("io.papermc.paper:paper-api:1.21.11-R0.1-SNAPSHOT")

        implementation(kotlin("stdlib"))
        implementation(kotlin("reflect"))
        implementation("io.github.anblusis:kommand-api:1.0.1")
        implementation("io.github.anblusis:tap-api:1.0.3")
        implementation("xyz.icetang.lib:invfx-api:3.3.3")
    }

    tasks.processResources {
        filesMatching("**/*.yml") {
            expand(project.properties)
        }
    }

    /*
    tasks.withType<Jar> {
        archiveBaseName.set(rootProject.name)
        archiveClassifier.set("")
        archiveVersion.set("")

        duplicatesStrategy = DuplicatesStrategy.EXCLUDE

        from(sourceSets["main"].output)

        dependsOn(configurations.runtimeClasspath)

        from({
            configurations.runtimeClasspath.get().filter { it.name.endsWith("jar") }.map { zipTree(it) }
        })
    }
     */

    tasks.register<Jar>("paperJar") {
        archiveBaseName.set(rootProject.name)
        archiveVersion.set("")
        from(sourceSets["main"].output)

        doLast {
            copy {
                from(archiveFile)
                val plugins = File(rootDir, ".paper/plugins/")
                into(if (File(plugins, archiveFileName.get()).exists()) File(plugins, "update") else plugins)
            }
        }
    }
}
