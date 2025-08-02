plugins {
    id("org.jetbrains.dokka")

    // due to late-binding not working, aggregation should define tasks doc-consumer
    id("org.javafreedom.aggregation-conventions")

    id("org.javafreedom.verification.jacoco-consumer-conventions")
    id("org.javafreedom.verification.test-consumer-conventions")
    id("org.javafreedom.documentation.documentation-consumer-conventions")

    id("org.javafreedom.verification.sonarqube-conventions")
}

allprojects {
    group = "org.javafreedom.gradle"
}

repositories {
    mavenCentral()
}

// Configure Dokka V2 for multi-module documentation
dokka {
    dokkaPublications.html {
        outputDirectory.set(layout.buildDirectory.dir("dokka"))
    }
}

dependencies {
    implementation(project(":app"))
    asciidoc(project(":documentation"))
}
