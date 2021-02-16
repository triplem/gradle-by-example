package org.javafreedom.documentation

import org.asciidoctor.gradle.jvm.AsciidoctorTask
import java.time.format.DateTimeFormatter
import java.time.LocalDateTime

plugins {
    id("org.asciidoctor.jvm.convert")
}

repositories {
    jcenter()
}

val revDate = System.getenv()["revdate"] ?: LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd"))
val revNumber = System.getenv()["revnumber"] ?: "DEV-Version"

tasks.getByName<AsciidoctorTask>("asciidoctor") {
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
