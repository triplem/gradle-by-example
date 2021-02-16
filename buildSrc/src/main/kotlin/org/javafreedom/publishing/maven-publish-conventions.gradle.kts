package org.javafreedom.publishing

import org.gradle.api.publish.maven.MavenPublication

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
            url = uri("file:///home/triplem/repo")
        }
    }
    publications {
        register<MavenPublication>("gpr") {
            from(components["java"])
        }
    }
}
