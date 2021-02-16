package org.javafreedom.verification

import io.gitlab.arturbosch.detekt.Detekt
import org.gradle.kotlin.dsl.creating
import org.gradle.kotlin.dsl.java
import org.gradle.kotlin.dsl.named
import org.gradle.kotlin.dsl.register

plugins {
    java
    id("io.gitlab.arturbosch.detekt")
}

val detektTasksSources: Configuration by configurations.creating {
    isVisible = false
    isCanBeResolved = true
    isCanBeConsumed = false
    extendsFrom(configurations.implementation.get())
    attributes {
        attribute(Usage.USAGE_ATTRIBUTE, objects.named(Usage.JAVA_RUNTIME))
        attribute(Category.CATEGORY_ATTRIBUTE, objects.named(Category.DOCUMENTATION))
        attribute(DocsType.DOCS_TYPE_ATTRIBUTE, objects.named("detekt-sources"))
    }
}

val aggregateDetekt = tasks.register<Detekt>("aggregateDetekt") {
    group = "Verification"
    description = "Aggregates detekt reports from all sub-projects"

    source(detektTasksSources.incoming.artifactView { lenient(true) }.files)

    buildUponDefaultConfig = false
    ignoreFailures = true

    reports {
        xml {
            enabled = true
        }
        html {
            enabled = true
        }
        txt {
            enabled = false
        }
    }
}

tasks.check {
    dependsOn(aggregateDetekt)
}