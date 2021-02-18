import io.gitlab.arturbosch.detekt.Detekt

plugins {
    id("org.jetbrains.dokka")

    id("org.javafreedom.verification.jacoco-consumer-conventions")
    id("org.javafreedom.verification.test-consumer-conventions")
    id("org.javafreedom.documentation.documentation-consumer-conventions")

    id("org.javafreedom.verification.sonarqube-conventions")
    id("org.javafreedom.aggregation-conventions")
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
    asciidoc(project(":documentation"))
}
