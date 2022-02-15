plugins {
    id("org.javafreedom.kotlin-library-conventions")
}

dependencies {
    implementation("org.apache.logging.log4j:log4j-core:2.16.0")

    testIntegrationImplementation("org.slf4j:slf4j-api:1.7.30")
}