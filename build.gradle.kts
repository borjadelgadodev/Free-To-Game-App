// Top-level build file where you can add configuration options common to all sub-projects/modules.
plugins {
    alias(libs.plugins.android.application) apply false
    alias(libs.plugins.kotlin.android) apply false
    alias(libs.plugins.kotlin.compose) apply false
    id("io.gitlab.arturbosch.detekt").version("1.23.7")
    alias(libs.plugins.jetbrains.kotlin.jvm) apply false
    alias(libs.plugins.ksp)
}

subprojects {
    apply(plugin = "io.gitlab.arturbosch.detekt")
    detekt {
        config.setFrom(file("$rootDir/config/detekt/detekt.yml"))
        dependencies {
            detektPlugins("io.gitlab.arturbosch.detekt:detekt-formatting:1.23.7")
        }
    }
}