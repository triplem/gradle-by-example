package org.javafreedom.publishing

import org.gradle.api.publish.maven.MavenPublication

val github_org: String by project

plugins {
    java
    `maven-publish`
}

java {
    withSourcesJar()
}

publishing {
    repositories {
        maven {
            name = "GitHubPackages"
            url = uri("https://maven.pkg.github.com/$github_org/${rootProject.name}")
            credentials {
                username = "i-dont-care"
                password = System.getenv("GITHUB_TOKEN")
            }
        }
    }
    publications {
        register<MavenPublication>("gpr") {
            from(components["java"])
        }
    }
}
