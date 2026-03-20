plugins {
    id("org.javafreedom.aggregation-conventions")
    id("org.javafreedom.verification.jacoco-consumer-conventions")
    id("org.javafreedom.verification.test-consumer-conventions")
    id("org.javafreedom.documentation.documentation-consumer-conventions")
    id("org.javafreedom.verification.sonarqube-conventions")
}

allprojects {
    group = "org.javafreedom.gradle"
}

dependencies {
    implementation(project(":app"))
    asciidoc(project(":documentation"))
}
