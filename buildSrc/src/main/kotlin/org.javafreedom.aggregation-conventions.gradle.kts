import io.gitlab.arturbosch.detekt.Detekt
import org.owasp.dependencycheck.reporting.ReportGenerator
import org.sonarqube.gradle.SonarTask

plugins {
    id("org.javafreedom.verification.jacoco-consumer-conventions")
    id("io.gitlab.arturbosch.detekt")
    id("org.sonarqube")
    id("org.owasp.dependencycheck")
}

// right now, there is no real aggregation of detekt, therefor we are just adding all
// sources to an aggregation tasks and generate the reports all in one
val aggregateDetektTask = tasks.register<Detekt>("aggregateDetekt") {
    buildUponDefaultConfig = false
    ignoreFailures = true

    reports {
        html.required
        xml.required
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
        sonarTestSources.add("src/testIntegration")
        sonarTestSources.add("src/test")
    }

    if (this.name != "documentation") {
        val reportsDir = this.buildDir.resolve("reports/detekt/detekt.xml").absolutePath
        val baseDir = this.projectDir

        val sonarTestSources = mutableListOf<String>()
        sonarTestSources.add("src/test")
        sonarTestSources.add("src/testIntegration")
        val testDirs = sonarTestSources.filter { baseDir.resolve(it).exists() }.joinToString()

        sonar {
            properties {
                property("sonar.sources", "src/main")
                property("sonar.kotlin.detekt.reportPaths", reportsDir)
                property("sonar.tests", testDirs)
            }
        }

        tasks.withType<SonarTask>().configureEach {
            shouldRunAfter("detekt")
        }
    }
}

dependencyCheck {
    failBuildOnCVSS = 3F
    formats = listOf(ReportGenerator.Format.HTML,
        ReportGenerator.Format.JUNIT, ReportGenerator.Format.XML, ReportGenerator.Format.SARIF)
    suppressionFile = "${rootProject.rootDir}/config/owasp/owasp-supression.xml"

    // remove plugin dependencies, for configs see
    // https://docs.gradle.org/current/userguide/java_plugin.html#sec:java_plugin_and_dependency_management
    val validConfigurations = listOf("compileClasspath", "runtimeClasspath", "default")
    scanConfigurations = configurations.names
            .filter { validConfigurations.contains(it) }
            .toList()

    outputDirectory = buildDir
        .resolve("reports")
        .resolve("owasp").path
}
