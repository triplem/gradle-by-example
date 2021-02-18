import org.jetbrains.kotlin.gradle.tasks.*

plugins {
    java
    kotlin("jvm")
    id("org.jetbrains.dokka")
    id("io.gitlab.arturbosch.detekt")

    // the following conventions depend on each other, keep them in the following order
    id("org.javafreedom.verification.test-producer-conventions")
    id("org.javafreedom.verification.jacoco-producer-conventions")
}

repositories {
    jcenter()
}

// Latest Java LTS Version
java {
    sourceCompatibility = JavaVersion.VERSION_11
    targetCompatibility = JavaVersion.VERSION_11
}

tasks.withType<KotlinCompile>().configureEach {
    kotlinOptions {
        jvmTarget = JavaVersion.VERSION_11.majorVersion
    }
}

detekt {
    buildUponDefaultConfig = false
    ignoreFailures = true

    reports {
        html.enabled = true
        xml.enabled = true
        txt.enabled = false
        sarif.enabled = false
    }
}

dependencies {
    constraints {
        // Define dependency versions as constraints
        implementation("org.apache.commons:commons-text:1.9")
    }

    // Align versions of all Kotlin components
    implementation(platform("org.jetbrains.kotlin:kotlin-bom"))

    // Use the Kotlin JDK 8 standard library.
    implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk8")

    // Add additonal dependencies useful for development
    //implementation("io.github.microutils:kotlin-logging:2.0.4")
    testImplementation(kotlin("test"))
    testImplementation(kotlin("test-junit5"))

    // Use JUnit Jupiter API for testing.
    testImplementation("org.junit.jupiter:junit-jupiter-api:5.7.0")

    // Use JUnit Jupiter Engine for testing.
    testRuntimeOnly("org.junit.jupiter:junit-jupiter-engine:5.7.0")
}
