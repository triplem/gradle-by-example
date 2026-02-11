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

// Inherit dependencies from test suite
configurations.named("integrationTestImplementation") {
    extendsFrom(configurations.getByName("testImplementation"))
}
configurations.named("integrationTestRuntimeOnly") {
    extendsFrom(configurations.getByName("testRuntimeOnly"))
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



tasks.withType<Test>().configureEach {
    useJUnitPlatform()
    testLogging {
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
    
    // Ensure test events are properly reported to IDEs
    systemProperty("junit.jupiter.execution.parallel.enabled", "false")
    systemProperty("junit.jupiter.testinstance.lifecycle.default", "per_class")
}
