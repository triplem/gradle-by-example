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
    toolchain {
        languageVersion.set(JavaLanguageVersion.of(11))
    }
}

tasks.withType<KotlinCompile>().configureEach {
    compilerOptions {
        jvmTarget.set(org.jetbrains.kotlin.gradle.dsl.JvmTarget.JVM_11)
    }
}

dependencies {
    // Kotlin dependencies
    implementation(kotlin("gradle-plugin", kotlinVersion))
    implementation(kotlin("bom", kotlinVersion))
    implementation(kotlin("reflect"))
    implementation(kotlin("stdlib"))
    
    // Documentation plugins
    implementation("org.jetbrains.dokka:dokka-gradle-plugin:1.9.20") {
        exclude(group = "org.jetbrains.kotlin", module = "kotlin-stdlib-jdk8")
    }
    implementation("org.asciidoctor:asciidoctor-gradle-jvm:4.0.3")
    
    // Code quality and security plugins
    implementation("org.sonarsource.scanner.gradle:sonarqube-gradle-plugin:5.1.0.4882")
    implementation("io.gitlab.arturbosch.detekt:detekt-gradle-plugin:1.23.7") {
        exclude(group = "org.jetbrains.kotlin", module = "kotlin-stdlib-jdk8")
    }
    implementation("org.owasp:dependency-check-gradle:10.0.4")
    
    // Build and deployment plugins  
    implementation("com.bmuschko:gradle-docker-plugin:9.4.0")
}
