import io.gitlab.arturbosch.detekt.Detekt

plugins {
    id("org.javafreedom.verification.jacoco-consumer-conventions")
    id("io.gitlab.arturbosch.detekt")
    id("org.sonarqube")
}

// right now, there is no real aggregation of detekt, therefor we are just adding all
// sources to an aggregation tasks and generate the reports all in one
val aggregateDetektTask = tasks.register<Detekt>("aggregateDetekt") {
    buildUponDefaultConfig = false
    ignoreFailures = true

    reports {
        html.required.set(true)
        xml.required.set(true)
        txt.required.set(false)
        sarif.required.set(false)
    }

    source(
        subprojects.flatMap { subproject ->
            subproject.tasks.filterIsInstance<Detekt>().map { task ->
                task.source
            }
        }
    )
}

subprojects {
    tasks.register("debug") {
        val sonarTestSources = mutableListOf<String>()
        sonarTestSources.add("src/integrationTest")
        sonarTestSources.add("src/test")
    }

    if (this.name != "documentation") {
        val reportsDir = this.layout.buildDirectory.dir("reports/detekt/detekt.xml").get().asFile.absolutePath
        val baseDir = this.projectDir

        val sonarTestSources = mutableListOf<String>()
        sonarTestSources.add("src/test")
        sonarTestSources.add("src/integrationTest")
        val testDirs = sonarTestSources.filter { baseDir.resolve(it).exists() }.joinToString()

        sonarqube {
            properties {
                property("sonar.sources", "src/main")
                property("sonar.kotlin.detekt.reportPaths", reportsDir)
                property("sonar.tests", testDirs)
            }
        }

        tasks.matching { it.name == "sonar" }.configureEach {
            shouldRunAfter("detekt")
        }
    }
}

