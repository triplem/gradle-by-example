import org.owasp.dependencycheck.reporting.ReportGenerator.Format

plugins {
    id("org.javafreedom.verification.jacoco-consumer-conventions")
    id("org.javafreedom.verification.test-consumer-conventions")
}

allprojects {
    group = "org.javafreedom.gradle"
}

repositories {
    jcenter()
}

dependencies {
    implementation(project(":app"))
}
