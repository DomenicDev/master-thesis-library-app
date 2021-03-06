/*
 * This file was generated by the Gradle 'init' task.
 */

plugins {
    // Apply the common convention plugin for shared build configuration between library and application projects.
    id("de.cassisi.common.kotlin-library-conventions")

}

dependencies {

    implementation("com.eventstore:db-client-java:3.0.1")
    constraints {
        implementation("com.fasterxml.jackson.core:jackson-databind:2.13.3") {
            because("higher version due to vulnerability in lower versions")
        }
    }

    implementation("com.google.code.gson:gson:2.9.0")

}
