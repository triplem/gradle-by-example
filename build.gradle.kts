plugins {
    id("org.jetbrains.dokka")
}

repositories {
    jcenter()
}

tasks.dokkaHtmlMultiModule.configure {
    outputDirectory.set(buildDir.resolve("dokkaCustomMultiModuleOutput"))
}
