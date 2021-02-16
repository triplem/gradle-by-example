package org.javafreedom.verification

import io.gitlab.arturbosch.detekt.Detekt
import org.gradle.kotlin.dsl.java
import org.gradle.kotlin.dsl.named

plugins {
    java
    id("io.gitlab.arturbosch.detekt")
}

// -----------------------------
// Add configuration to allow aggregation of detekt-reports
// -----------------------------
configurations.create("detektTasksSources") {
    isVisible = false
    isCanBeResolved = false
    isCanBeConsumed = true
    extendsFrom(configurations.implementation.get())
    attributes {
        attribute(Category.CATEGORY_ATTRIBUTE, objects.named(Category.DOCUMENTATION))
        attribute(DocsType.DOCS_TYPE_ATTRIBUTE, objects.named("detekt-sources"))
    }

    tasks.detekt.get().source.forEach {
        outgoing.artifact(it)
    }
}

tasks.named<Detekt>("detekt") {
    buildUponDefaultConfig = false
    ignoreFailures = true
}
