import org.jetbrains.kotlin.gradle.tasks.KotlinCompile
import java.util.Properties

val properties = Properties().also { props ->
    project.projectDir.resolveSibling("gradle.properties").bufferedReader().use {
        props.load(it)
    }
}
val kotlinVersion: String = properties.getProperty("kotlinVersion")

plugins {
    // Support convention plugins written in Kotlin. Convention plugins are build scripts in 'src/main' that automatically become available as plugins in the main build.
    `kotlin-dsl`
}

repositories {
    // Use the plugin portal to apply community plugins in convention plugins.
    gradlePluginPortal()
    mavenCentral()
}

java {
    sourceCompatibility = JavaVersion.VERSION_11
}

tasks.withType<KotlinCompile>().configureEach {
    kotlinOptions.jvmTarget = "11"
}

dependencies {
    implementation(kotlin("gradle-plugin", kotlinVersion))
    implementation(kotlin("bom", kotlinVersion))
    implementation(kotlin("reflect"))
    implementation(kotlin("stdlib"))
    implementation("org.jetbrains.dokka:dokka-gradle-plugin:1.7.20") {
        exclude(group = "org.jetbrains.kotlin", module = "kotlin-stdlib-jdk8")
    }
    implementation("org.sonarsource.scanner.gradle:sonarqube-gradle-plugin:3.5.0.2730")
    implementation("io.gitlab.arturbosch.detekt:detekt-gradle-plugin:1.22.0") {
        exclude(group = "org.jetbrains.kotlin", module = "kotlin-stdlib-jdk8")
    }
    implementation("org.owasp:dependency-check-gradle:8.0.1")
    implementation("org.asciidoctor:asciidoctor-gradle-jvm:4.0.0-alpha.1")
    implementation("com.bmuschko:gradle-docker-plugin:9.1.0")
}
