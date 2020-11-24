import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

val coroutinesVersion = "1.4.1"
val jdaVersion = "4.2.0_222"
val lavaPlayerVersion = "1.3.61"
val slf4jVersion = "1.7.30"
val hopliteVersion = "1.3.8"

plugins {
    kotlin("jvm") version "1.4.20"
}
group = "com.vacegaming.james"
version = "1.0-SNAPSHOT"

repositories {
    jcenter()
    mavenCentral()
}

dependencies {
    implementation(kotlin("stdlib-jdk8"))

    /**
     * Kotlin
     */
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:$coroutinesVersion")

    /**
     * Discord
     */
    implementation("net.dv8tion:JDA:$jdaVersion")
    implementation("com.sedmelluq:lavaplayer:$lavaPlayerVersion")

    /**
     * Application
     */
    implementation("org.slf4j:slf4j-simple:$slf4jVersion")
    implementation("com.sksamuel.hoplite:hoplite-core:$hopliteVersion")
    implementation("com.sksamuel.hoplite:hoplite-json:$hopliteVersion")
}

tasks {
    compileKotlin {
        kotlinOptions.jvmTarget = "11"
    }
}

val compileKotlin: KotlinCompile by tasks
compileKotlin.kotlinOptions {
    languageVersion = "1.4"
}
