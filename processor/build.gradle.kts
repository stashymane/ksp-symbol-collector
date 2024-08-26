plugins {
    alias(libs.plugins.kotlinMultiplatform)
    alias(libs.plugins.publish)
    id("signing")
}

kotlin {
    jvm()

    sourceSets {
        jvmMain.dependencies {
            implementation(libs.ksp.api)
            implementation(projects.model)
        }
        jvmTest.dependencies {
//            implementation(libs.ksp.test)
        }
    }
}
