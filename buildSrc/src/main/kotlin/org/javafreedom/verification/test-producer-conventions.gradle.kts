package org.javafreedom.verification

import org.gradle.api.tasks.testing.logging.TestExceptionFormat
import org.gradle.api.tasks.testing.logging.TestLogEvent

val libs = extensions.getByType<VersionCatalogsExtension>().named("libs")

plugins {
    kotlin("jvm")
    id("jvm-test-suite")
    id("idea")
}

// -----------------------------
// Configure test suites using the modern test-suites plugin
// -----------------------------

testing {
    suites {
        val test by getting(JvmTestSuite::class) {
            useJUnitJupiter()
        }
        
        val integrationTest by registering(JvmTestSuite::class) {
            dependencies {
                implementation(project())
                // Inherit all test dependencies
                implementation(platform(libs.findLibrary("kotlin-bom").get()))
                implementation(libs.findLibrary("kotlin-stdlib").get())
                implementation(libs.findLibrary("kotlinLogging").get())
                implementation(libs.findLibrary("kotlin-test").get())
                implementation(libs.findLibrary("kotlin-test-junit5").get())
                implementation(libs.findLibrary("junitJupiterApi").get())
                implementation(libs.findLibrary("assertkJvm").get())
                runtimeOnly(libs.findLibrary("junitJupiterEngine").get())
            }
            
            targets {
                all {
                    testTask.configure {
                        description = "Runs integration tests."
                        group = "Verification"
                        shouldRunAfter(test)
                    }
                }
            }
        }
    }
}

// Configure Kotlin compilation to allow internal visibility for integration tests
afterEvaluate {
    kotlin.target.compilations.named("integrationTest") {
        associateWith(kotlin.target.compilations.named("main").get())
    }
}

tasks.check {
    dependsOn(testing.suites.named("integrationTest"))
}

// IDEA integration is handled automatically by test-suites plugin

// -----------------------------
// Add configuration to allow aggregation of unit-test-reports
// -----------------------------

// Share the test report data to be aggregated for the whole project
configurations.create("binaryTestResultsElements") {
    isVisible = false
    isCanBeResolved = false
    isCanBeConsumed = true
    extendsFrom(configurations.implementation.get())
    attributes {
        attribute(Category.CATEGORY_ATTRIBUTE, objects.named(Category.DOCUMENTATION))
        attribute(DocsType.DOCS_TYPE_ATTRIBUTE, objects.named("test-report-data"))
    }

    outgoing.artifact(tasks.test.map { task -> task.binaryResultsDirectory.get() })
    outgoing.artifact(tasks.named("integrationTest").map { task -> 
        (task as Test).binaryResultsDirectory.get() 
    })
}

tasks.withType<Test>().configureEach {
    useJUnitPlatform()
    testLogging {
        useJUnitPlatform()
        events = setOf(
            TestLogEvent.FAILED,
            TestLogEvent.PASSED,
            TestLogEvent.SKIPPED,
            TestLogEvent.STANDARD_OUT
        )
        exceptionFormat = TestExceptionFormat.FULL
        showExceptions = true
        showCauses = true
        showStackTraces = true
    }
}
