import org.jetbrains.kotlin.gradle.ExperimentalWasmDsl
import org.jetbrains.kotlin.gradle.tasks.KotlinCompilationTask

// running KSP on the commonMain source set is currently janky - see https://github.com/google/ksp/issues/567

plugins {
    alias(libs.plugins.kotlinMultiplatform)
    alias(libs.plugins.ksp)
}

kotlin {
    jvm()
    @OptIn(ExperimentalWasmDsl::class)
    wasmJs()

    sourceSets {
        commonMain {
            // kotlin does not see our generated sources until the directory is included
            kotlin.srcDir("build/generated/ksp/metadata/commonMain/kotlin")

            dependencies {
                implementation(projects.model)
            }
        }
    }
}

dependencies {
    /* the list below is of tasks to run the processor on - if you, for example, only use your annotation in `commonMain`,
     you can have only `kspCommonMainMetadata` here. Otherwise, you have to include the KSP task for each task that
     requires it, e.g. `kspJvm`, etc. */
    listOf("kspCommonMainMetadata").forEach {
        add(it, projects.processor)
    }
}

/*
    The snippet below only works when there is more than one target. The KMP plugin does not create the commonMain task otherwise.
    This should ideally be fixed by the KSP plugin, so that the common module is included in all builds, but for now we need a workaround.
*/
tasks.withType<KotlinCompilationTask<*>>().configureEach {
    val target = "kspCommonMainKotlinMetadata"
    if (name != target)
        dependsOn(target)
}
