plugins {
    id("org.javafreedom.kotlin-application-conventions")
}

dependencies {
    implementation("org.apache.commons:commons-text")
    implementation(project(":utilities"))
}

application {
    // Define the main class for the application.
    mainClass.set("org.javafreedom.app.AppKt")
}
