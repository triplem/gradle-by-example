import org.jetbrains.kotlin.gradle.tasks.*
import java.util.Properties

val properties = Properties().also { props ->
    project.projectDir.resolveSibling("../gradle.properties").bufferedReader().use {
        props.load(it)
    }
}
val junitVersion: String = properties.getProperty("junitVersion")

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
    mavenCentral()
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
}

dependencies {
    constraints {
        // Define dependency versions as constraints
        implementation("org.apache.commons:commons-text:1.10.0")
    }

    // Align versions of all Kotlin components
    implementation(platform("org.jetbrains.kotlin:kotlin-bom"))

    // Use the Kotlin JDK 8 standard library.
    implementation("org.jetbrains.kotlin:kotlin-stdlib")

    // Add additonal dependencies useful for development
    implementation("io.github.microutils:kotlin-logging:3.0.4")
    testImplementation("com.willowtreeapps.assertk:assertk-jvm:0.25")
    testImplementation(kotlin("test"))
    testImplementation(kotlin("test-junit5"))

    // Use JUnit Jupiter API for testing.
    testImplementation("org.junit.jupiter:junit-jupiter-api:$junitVersion")

    // Use JUnit Jupiter Engine for testing.
    testRuntimeOnly("org.junit.jupiter:junit-jupiter-engine:$junitVersion")
}
