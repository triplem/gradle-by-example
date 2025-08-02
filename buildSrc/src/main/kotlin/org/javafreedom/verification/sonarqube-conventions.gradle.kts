package org.javafreedom.verification

plugins {
    `java-library`
    id("org.sonarqube")
}

val github_org: String by project
val github_project_url = "https://github.com/${github_org}/${rootProject.name}"

sonar {
    properties {
        // See https://docs.sonarqube.org/display/SCAN/Analyzing+with+SonarQube+Scanner+for+Gradle#AnalyzingwithSonarQubeScannerforGradle-Configureanalysisproperties
        property("sonar.sourceEncoding", "UTF-8")
        property("sonar.projectName", rootProject.name)
        property("sonar.projectKey",
            System.getenv()["SONAR_PROJECT_KEY"] ?: (github_org + "_" + rootProject.name)
        )
        property("sonar.organization", System.getenv()["SONAR_ORGANIZATION"] ?: github_org)
        property("sonar.projectVersion", rootProject.version.toString())
        property("sonar.host.url", System.getenv()["SONAR_HOST_URL"] ?: "https://sonarcloud.io")
        property("sonar.token", System.getenv()["SONAR_TOKEN"] ?: "" )
        property("sonar.scm.provider", "git")
        property("sonar.links.homepage", github_project_url)
        property("sonar.links.ci", "$github_project_url/actions")
        property("sonar.links.scm", github_project_url)
        property("sonar.links.issue", "$github_project_url/issues")
        property("sonar.coverage.jacoco.xmlReportPaths", layout.buildDirectory.dir("reports/jacoco/aggregateJacocoTestReport/aggregateJacocoTestReport.xml").get().asFile)
    }
}

tasks.matching { it.name == "sonar" }.configureEach {
    dependsOn(project.tasks.named("aggregateJacocoTestReport"))
}

//tasks.named("check") {
//    dependsOn("sonar")
//}