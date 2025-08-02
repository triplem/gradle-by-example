package org.javafreedom.documentation

import io.gitlab.arturbosch.detekt.Detekt

val asciidoc by configurations.creating {
    isCanBeResolved = true
    isVisible = true
    isCanBeConsumed = false
    attributes {
        attribute(Category.CATEGORY_ATTRIBUTE, objects.named(Category.DOCUMENTATION))
        attribute(DocsType.DOCS_TYPE_ATTRIBUTE, objects.named("asciidoc-html-folder"))
    }
}

val dokkaGenerateTask = tasks.named("dokkaGenerate")
val testReportTask = tasks.named("testReport")
val jacocoReportTask = tasks.named("aggregateJacocoTestReport")
val detektReportTask = tasks.named<Detekt>("aggregateDetekt")
val rootDetektTask = tasks.named("detekt")

tasks.register<Copy>("aggregateReports") {
    dependsOn(dokkaGenerateTask)
    dependsOn(testReportTask)
    dependsOn(jacocoReportTask)
    dependsOn(detektReportTask)
    dependsOn(rootDetektTask)

    // Configure as proper Copy task for configuration cache compatibility
    destinationDir = layout.buildDirectory.dir("documentation").get().asFile
    
    from(layout.buildDirectory.dir("dokka")) {
        into("dokka")
    }
    
    from(layout.buildDirectory.dir("reports/allTests")) {
        into("tests")
    }
    
    from(layout.buildDirectory.dir("reports/jacoco/aggregateJacocoTestReport")) {
        into("jacoco")
    }
    
    from(layout.buildDirectory.dir("reports/detekt")) {
        into("detekt")
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
            into(targetDir.resolve("pages"))
            from(asciidoc.incoming.artifactView { lenient(true) }.files)
        }

        copy {
            into(targetDir)
            from("src/documentation")
        }
    }
}
