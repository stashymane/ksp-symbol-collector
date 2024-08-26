rootProject.name = "ksp-symbol-collector"
enableFeaturePreview("TYPESAFE_PROJECT_ACCESSORS")

dependencyResolutionManagement {
    repositories {
        mavenCentral()
    }
}

include(":model", ":processor", ":test-proj")