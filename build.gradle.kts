import org.owasp.dependencycheck.reporting.ReportGenerator.Format

plugins {
    id("org.jetbrains.dokka")

    id("org.javafreedom.verification.jacoco-consumer-conventions")
    id("org.javafreedom.verification.test-consumer-conventions")
}

allprojects {
    group = "org.javafreedom.gradle"
}

repositories {
    jcenter()
}

// this task generates all tasks for sub-projects itself, therefor it just needs
// to be applied on the root project, conventions are not working :-(
tasks.dokkaHtmlMultiModule.configure {
    outputDirectory.set(buildDir.resolve("dokka"))
}

dependencies {
    implementation(project(":app"))
}
