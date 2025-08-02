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
    
    configurations.all {
        resolutionStrategy {
            force("org.junit:junit-bom:5.11.0")
            eachDependency {
                if (requested.group == "org.junit" && requested.name == "junit-bom") {
                    useVersion("5.11.0")
                }
            }
        }
    }
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
