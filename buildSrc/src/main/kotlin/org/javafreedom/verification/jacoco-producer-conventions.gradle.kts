package org.javafreedom.verification

import org.gradle.api.tasks.SourceSetContainer
import org.gradle.api.tasks.testing.Test
import org.gradle.kotlin.dsl.getByType

plugins {
    // Apply the org.jetbrains.kotlin.jvm Plugin to add support for Kotlin.
    kotlin("jvm")
    id("jacoco")
}

// -----------------------------
// Add jacocoTest to all projects as well as an aggregation for the coverage reports
// -----------------------------

// Do not generate reports for individual projects
tasks.jacocoTestReport {
    enabled = false
}

val sourcesets = project.extensions.getByType(SourceSetContainer::class)

//https://docs.gradle.org/current/samples/sample_jvm_multi_project_with_code_coverage.html
// Share sources folder with other projects for aggregated JaCoCo reports
configurations.create("transitiveSourcesElements") {
    isVisible = false
    isCanBeResolved = false
    isCanBeConsumed = true
    extendsFrom(configurations.implementation.get())
    attributes {
        attribute(Usage.USAGE_ATTRIBUTE, objects.named(Usage.JAVA_RUNTIME))
        attribute(Category.CATEGORY_ATTRIBUTE, objects.named(Category.DOCUMENTATION))
        attribute(DocsType.DOCS_TYPE_ATTRIBUTE, objects.named("source-folders"))
    }

    sourcesets.main.get().allJava.srcDirs.forEach {
        outgoing.artifact(it)
    }
}

// Share the coverage data to be aggregated for the whole product
configurations.create("coverageDataElements") {
    isVisible = false
    isCanBeResolved = false
    isCanBeConsumed = true
    extendsFrom(configurations.implementation.get())
    attributes {
        attribute(Usage.USAGE_ATTRIBUTE, objects.named(Usage.JAVA_RUNTIME))
        attribute(Category.CATEGORY_ATTRIBUTE, objects.named(Category.DOCUMENTATION))
        attribute(DocsType.DOCS_TYPE_ATTRIBUTE, objects.named("jacoco-coverage-data"))
    }

    // This will cause the test task to run if the coverage data is requested by the aggregation task
    outgoing.artifact(tasks.test.map { task ->
        task.extensions.getByType<JacocoTaskExtension>().destinationFile!!
    })

    outgoing.artifact(tasks.named<Test>("integrationTest").map { task ->
        task.extensions.getByType<JacocoTaskExtension>().destinationFile!!
    })

}

// Share the output folder of each project with the coverage report
configurations.create("coverageClassElements") {
    isVisible = false
    isCanBeResolved = false
    isCanBeConsumed = true
    extendsFrom(configurations.implementation.get())
    attributes {
        attribute(Usage.USAGE_ATTRIBUTE, objects.named(Usage.JAVA_RUNTIME))
        attribute(Category.CATEGORY_ATTRIBUTE, objects.named(Category.DOCUMENTATION))
        attribute(DocsType.DOCS_TYPE_ATTRIBUTE, objects.named("output-folders"))
    }

    sourcesets.main.get().output.classesDirs.files.forEach {
        outgoing.artifact(it)
    }
}

