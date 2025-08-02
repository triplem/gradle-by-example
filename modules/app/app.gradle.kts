plugins {
    id("org.javafreedom.kotlin-application-conventions")
}

dependencies {
    implementation(libs.commonsText)
    implementation(project(":utilities"))
}

application {
    // Define the main class for the application.
    mainClass.set("org.javafreedom.app.AppKt")
}
