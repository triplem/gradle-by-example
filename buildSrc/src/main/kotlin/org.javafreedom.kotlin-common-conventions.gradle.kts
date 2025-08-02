import org.jetbrains.kotlin.gradle.tasks.*

val libs = extensions.getByType<VersionCatalogsExtension>().named("libs")

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
    toolchain {
        languageVersion.set(JavaLanguageVersion.of(21))
    }
}

tasks.withType<KotlinCompile>().configureEach {
    compilerOptions {
        jvmTarget.set(org.jetbrains.kotlin.gradle.dsl.JvmTarget.JVM_21)
    }
}

detekt {
    buildUponDefaultConfig = false
    ignoreFailures = true
}


dependencies {
    constraints {
        // Define dependency versions as constraints
        implementation(libs.findLibrary("commonsText").get())
    }

    // Align versions of all Kotlin components
    implementation(platform(libs.findLibrary("kotlin-bom").get()))

    // Use the Kotlin standard library
    implementation(libs.findLibrary("kotlin-stdlib").get())

    // Add additional dependencies useful for development
    implementation(libs.findLibrary("kotlinLogging").get())
    
    // Testing dependencies
    testImplementation(libs.findBundle("testing").get())
    testRuntimeOnly(libs.findBundle("testingRuntime").get())
}
