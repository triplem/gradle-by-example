import org.gradle.testing.jacoco.tasks.JacocoReport
import org.jetbrains.kotlin.gradle.plugin.KotlinTarget

plugins {
    // Apply the org.jetbrains.kotlin.jvm Plugin to add support for Kotlin.
    kotlin("jvm")
//    id("org.jetbrains.dokka")
//    id("idea")
    id("jacoco")
}

repositories {
    // Use JCenter for resolving dependencies.
    jcenter()
}



// to get rid of "Overload resolution ambiguity"-messsage
val sourcesets = project.extensions.getByType(SourceSetContainer::class)
val testIntegration by sourcesets.creating

configurations[testIntegration.implementationConfigurationName]
    .extendsFrom(configurations.testImplementation.get())
configurations[testIntegration.runtimeOnlyConfigurationName]
    .extendsFrom(configurations.testRuntimeOnly.get())

val koTarget: KotlinTarget = kotlin.target
koTarget.compilations.getByName("testIntegration") {
    associateWith(target.compilations.getByName("main"))
}

val integrationTestTask = tasks.register<Test>("integrationTest") {
    description = "Runs integration tests."
    group = "verification"

    testClassesDirs = testIntegration.output.classesDirs
    classpath = configurations[testIntegration.runtimeClasspathConfigurationName] + testIntegration.output

    useJUnitPlatform()

    shouldRunAfter(tasks.test)
}

tasks.check {
    dependsOn(integrationTestTask)
}

tasks.test {
    useJUnitPlatform()
}

// Do not generate reports for individual projects
tasks.jacocoTestReport {
    enabled = false
}

//https://docs.gradle.org/current/samples/sample_jvm_multi_project_with_code_coverage.html
// Share sources folder with other projects for aggregated JaCoCo reports
configurations.create("transitiveSourcesElements") {
    isVisible = false
    isCanBeResolved = false
    isCanBeConsumed = true
    extendsFrom(configurations.implementation.get())
    attributes {
        attribute(Usage.USAGE_ATTRIBUTE, objects.named(Usage.JAVA_RUNTIME))
        attribute(Category.CATEGORY_ATTRIBUTE, objects.named(Category.DOCUMENTATION))
        attribute(DocsType.DOCS_TYPE_ATTRIBUTE, objects.named("source-folders"))
    }
    sourcesets.main.get().allJava.srcDirs.forEach {
        outgoing.artifact(it)
    }
}

// Share the coverage data to be aggregated for the whole product
configurations.create("coverageDataElements") {
    isVisible = false
    isCanBeResolved = false
    isCanBeConsumed = true
    extendsFrom(configurations.implementation.get())
    attributes {
        attribute(Usage.USAGE_ATTRIBUTE, objects.named(Usage.JAVA_RUNTIME))
        attribute(Category.CATEGORY_ATTRIBUTE, objects.named(Category.DOCUMENTATION))
        attribute(DocsType.DOCS_TYPE_ATTRIBUTE, objects.named("jacoco-coverage-data"))
    }
    // This will cause the test task to run if the coverage data is requested by the aggregation task
    outgoing.artifact(tasks.test.map { task ->
        task.extensions.getByType<JacocoTaskExtension>().destinationFile!!
    })

    outgoing.artifact(integrationTestTask.map { task ->
        task.extensions.getByType<JacocoTaskExtension>().destinationFile!!
    })
}

// Share the output folder of each project with the coverage report
configurations.create("coverageClassElements") {
    isVisible = false
    isCanBeResolved = false
    isCanBeConsumed = true
    extendsFrom(configurations.implementation.get())
    attributes {
        attribute(Usage.USAGE_ATTRIBUTE, objects.named(Usage.JAVA_RUNTIME))
        attribute(Category.CATEGORY_ATTRIBUTE, objects.named(Category.DOCUMENTATION))
        attribute(DocsType.DOCS_TYPE_ATTRIBUTE, objects.named("output-folders"))
    }
    sourcesets.main.get().output.classesDirs.files.forEach {
        outgoing.artifact(it)
    }
}

// Share the test report data to be aggregated for the whole project
configurations.create("binaryTestResultsElements") {
    isVisible = false
    isCanBeResolved = false
    isCanBeConsumed = true
    attributes {
        attribute(Category.CATEGORY_ATTRIBUTE, objects.named(Category.DOCUMENTATION))
        attribute(DocsType.DOCS_TYPE_ATTRIBUTE, objects.named("test-report-data"))
    }

    outgoing.artifact(tasks.test.map { task -> task.binaryResultsDirectory.get() })
    outgoing.artifact(integrationTestTask.map { task -> task.binaryResultsDirectory.get() })
}

dependencies {
    constraints {
        // Define dependency versions as constraints
        implementation("org.apache.commons:commons-text:1.9")
        implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk8")
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

    // add module internal dependencies
//    "testIntegrationImplementation"(project)

    // Use JUnit Jupiter Engine for testing.
    testRuntimeOnly("org.junit.jupiter:junit-jupiter-engine")
}

//idea {
//    module {
//        val testSources = testSourceDirs
//        testSources.addAll(testIntegration.allJava.srcDirs)
//        testSourceDirs = testSources
//
//        val testResources = testResourceDirs
//        testResources.addAll(testIntegration.resources.srcDirs)
//        testResourceDirs = testResources
//    }
//}
