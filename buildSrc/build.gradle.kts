import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    // Support convention plugins written in Kotlin. Convention plugins are build scripts in 'src/main' that automatically become available as plugins in the main build.
    `kotlin-dsl`
}

val libs = extensions.getByType<VersionCatalogsExtension>().named("libs")

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
    implementation(libs.findLibrary("kotlinGradlePlugin").get())
    implementation(libs.findLibrary("kotlin-bom").get())
    implementation(libs.findLibrary("kotlin-reflect").get())
    implementation(libs.findLibrary("kotlin-stdlib").get())
    
    // Documentation plugins
    implementation(libs.findLibrary("dokkaGradlePlugin").get()) {
        exclude(group = "org.jetbrains.kotlin", module = "kotlin-stdlib-jdk8")
    }
    implementation(libs.findLibrary("asciidoctorGradlePlugin").get())
    
    // Code quality and security plugins
    implementation(libs.findLibrary("sonarqubeGradlePlugin").get())
    implementation(libs.findLibrary("detektGradlePlugin").get()) {
        exclude(group = "org.jetbrains.kotlin", module = "kotlin-stdlib-jdk8")
    }
    implementation(libs.findLibrary("owaspDependencyCheckGradlePlugin").get())
    
    // Build and deployment plugins  
    implementation(libs.findLibrary("dockerGradlePlugin").get())
}
