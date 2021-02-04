plugins {
    id("org.jetbrains.dokka")
    id("org.sonarqube")
    id("jacoco")
}

repositories {
    jcenter()
}

tasks.dokkaHtmlMultiModule.configure {
    outputDirectory.set(buildDir.resolve("dokkaCustomMultiModuleOutput"))
}

val testReportData by configurations.creating {
    isCanBeResolved = true
    isCanBeConsumed = false
    attributes {
        attribute(Category.CATEGORY_ATTRIBUTE, objects.named(Category.DOCUMENTATION))
        attribute(DocsType.DOCS_TYPE_ATTRIBUTE, objects.named("test-report-data"))
    }
}

dependencies {
    testReportData(project(":app"))
    testReportData(project(":list"))
    testReportData(project(":utilities"))
}

tasks.register<TestReport>("testReport") {
    destinationDir = file("$buildDir/reports/allTests")
    // Use test results from testReportData configuration
    (getTestResultDirs() as ConfigurableFileCollection).from(testReportData)
}
