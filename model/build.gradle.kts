plugins {
    alias(libs.plugins.kotlinMultiplatform)
    alias(libs.plugins.publish)
    id("signing")
}
@OptIn(org.jetbrains.kotlin.gradle.ExperimentalWasmDsl::class)
kotlin {
    jvm()

    mingwX64()
    linuxX64()
    linuxArm64()

    js {
        nodejs()
        browser()
    }

    wasmJs {
        browser()
        d8()
    }
    wasmWasi {
        nodejs()
    }

    iosX64()
    iosArm64()
    iosSimulatorArm64()

    macosX64()
    macosArm64()

    watchosX64()
    watchosArm32()
    watchosArm64()
    watchosDeviceArm64()
    watchosSimulatorArm64()
}