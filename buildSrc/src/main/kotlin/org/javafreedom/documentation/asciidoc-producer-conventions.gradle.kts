package org.javafreedom.documentation

import org.asciidoctor.gradle.jvm.AsciidoctorTask
import java.time.format.DateTimeFormatter
import java.time.LocalDateTime

plugins {
    id("org.asciidoctor.jvm.convert")
}

repositories {
    mavenCentral()
}

val revDate = System.getenv()["revdate"] ?: LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd"))
val revNumber = System.getenv()["revnumber"] ?: "DEV-Version"

val asciidoctorTask = tasks.named<AsciidoctorTask>("asciidoctor") {
    setSourceDir(file("docs"))
    setOutputDir(file("$buildDir/docs"))

    resources {
        from("docs/resources") {
            include("*.png")
        }

        into("./resources")
    }

    attributes(
        mapOf(
            "source-highlighter" to "rouge",
            "toc"                to "left",
            "toclevels"          to 2,
            "idprefix"           to "",
            "idseparator"        to "-",
            "revnumber"          to "$revNumber",
            "revdate"            to "$revDate"
        )
    )
}

configurations.create("asciidoctorHtmlFolder") {
    isVisible = false
    isCanBeResolved = false
    isCanBeConsumed = true
    attributes {
        attribute(Category.CATEGORY_ATTRIBUTE, objects.named(Category.DOCUMENTATION))
        attribute(DocsType.DOCS_TYPE_ATTRIBUTE, objects.named("asciidoc-html-folder"))
    }

    outgoing.artifact(asciidoctorTask.map { task ->
        task.outputDir
    })
}
