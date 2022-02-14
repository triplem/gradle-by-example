plugins {
    id("org.javafreedom.kotlin-application-conventions")
}

dependencies {
    implementation("org.apache.commons:commons-text")
    implementation(project(":utilities"))

    implementation("org.apache.logging.log4j:log4j-core:2.16.0")
}

application {
    // Define the main class for the application.
    mainClass.set("org.javafreedom.app.AppKt")
}
