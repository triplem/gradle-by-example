plugins {
    id("org.javafreedom.kotlin-common-conventions")
    id("org.javafreedom.publishing.maven-publish-conventions")

    id("com.bmuschko.docker-java-application")
    application
}

val github_org: String by project
val dockerTag = System.getenv()["revnumber"] ?: "latest"

docker {
    javaApplication {
        baseImage.set("eclipse-temurin:11-jre")
        maintainer.set("Gradle-By-Example-Team 'gbex@somewhere.com'")
        jvmArgs.set(listOf("-server", "-XX:+UnlockExperimentalVMOptions", "-XX:InitialRAMFraction=2",
            "-XX:MinRAMFraction=2", "-XX:MaxRAMFraction=2", "-XX:+UseG1GC",
            "-XX:MaxGCPauseMillis=100", "-XX:+UseStringDeduplication"))
        ports.set(listOf(8080))
        mainClassName.set(project.application.mainClass)
        images.set(listOf("${project.group}/${rootProject.name}:latest",
            "ghcr.io/${github_org}/${rootProject.name}:${dockerTag}"))
    }
}
