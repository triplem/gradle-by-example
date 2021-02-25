package org.javafreedom.documentation

import org.asciidoctor.gradle.jvm.AsciidoctorTask
import org.jetbrains.dokka.gradle.DokkaMultiModuleTask

val asciidoc by configurations.creating {
    isCanBeResolved = true
    isVisible = true
    isCanBeConsumed = false
    attributes {
        attribute(Category.CATEGORY_ATTRIBUTE, objects.named(Category.DOCUMENTATION))
        attribute(DocsType.DOCS_TYPE_ATTRIBUTE, objects.named("asciidoc-html-folder"))
    }
}

val dokkaHtmlMultiModuleTask = tasks.named<DokkaMultiModuleTask>("dokkaHtmlMultiModule")
val testReportTask = tasks.named("testReport")
val jacocoReportTask = tasks.named("aggregateJacocoTestReport")

tasks.register("aggregateReports") {
    dependsOn(dokkaHtmlMultiModuleTask)
    dependsOn(testReportTask)

    doLast {
        val targetDir = buildDir.resolve("documentation").toPath()

        copy {
            into(targetDir.resolve("dokka"))
            from(dokkaHtmlMultiModuleTask.map { task -> task.outputDirectory })
        }

        copy {
            into(targetDir.resolve("tests"))
            from(testReportTask.map { task -> task.outputs })
        }
    }
}

tasks.register("aggregateDocumentation") {
    asciidoc.dependencies
        .filterIsInstance<ProjectDependency>()
        .map { it.dependencyProject.tasks.withType<AsciidoctorTask>() }
        .forEach { dependsOn(it) }

    doLast {
        val targetDir = buildDir.resolve("documentation").toPath()

        copy {
            into(targetDir.resolve("pages"))
            from(asciidoc.incoming.artifactView { lenient(true) }.files)
        }

        copy {
            into(targetDir)
            from("src/documentation")
        }
    }
}
