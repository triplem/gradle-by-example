package org.javafreedom.documentation

import io.gitlab.arturbosch.detekt.Detekt
import org.jetbrains.dokka.gradle.DokkaMultiModuleTask
import org.owasp.dependencycheck.gradle.extension.DependencyCheckExtension
import org.owasp.dependencycheck.gradle.tasks.Aggregate

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
val detektReportTask = tasks.named<Detekt>("aggregateDetekt")
val dependencyCheckTask = tasks.named<Aggregate>("dependencyCheckAggregate")

tasks.register("aggregateReports") {
    dependsOn(dokkaHtmlMultiModuleTask)
    dependsOn(testReportTask)
    dependsOn(jacocoReportTask)
    dependsOn(detektReportTask)

    doLast {
        val targetDir = layout.buildDirectory.dir("documentation").get().asFile.toPath()

        copy {
            into(targetDir.resolve("dokka"))
            from(dokkaHtmlMultiModuleTask.map { task -> task.outputDirectory })
        }

        copy {
            into(targetDir.resolve("tests"))
            from(testReportTask.map { task -> task.outputs })
        }

        copy {
            into(targetDir.resolve("jacoco"))
            from(jacocoReportTask.map { task -> task.outputs })
        }

        copy {
            into(targetDir.resolve("detekt"))
            from(detektReportTask.map { task -> task.outputs })
        }
    }
}

tasks.register("aggregateDocumentation") {
    // Depend on all asciidoctor tasks from projects in the asciidoc configuration
    dependsOn(provider {
        asciidoc.dependencies
            .filterIsInstance<ProjectDependency>()
            .map { "${it.name}:asciidoctor" }
    })

    doLast {
        val targetDir = layout.buildDirectory.dir("documentation").get().asFile.toPath()

        copy {
            into(targetDir.resolve("owasp"))
            project.extensions.findByType<DependencyCheckExtension>()?.let {
                from(it.outputDirectory)
            }
        }

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
