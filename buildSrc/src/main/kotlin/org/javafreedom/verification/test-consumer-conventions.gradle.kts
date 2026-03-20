package org.javafreedom.verification

plugins {
    kotlin("jvm")
    id("test-report-aggregation")
}

dependencies {
    testReportAggregation(project(":app"))
    testReportAggregation(project(":list"))
    testReportAggregation(project(":utilities"))
}

reporting {
    reports {
        val integrationTestAggregateTestReport by creating(org.gradle.api.tasks.testing.AggregateTestReport::class)
    }
}