import org.jetbrains.kotlin.gradle.tasks.KotlinCompile
import com.github.jengelman.gradle.plugins.shadow.tasks.ShadowJar

val implementationVersion = "1.1"

val kotlinVersion = "1.4.20"
val coroutinesVersion = "1.4.1"
val jdaVersion = "4.2.0_222"
val lavaPlayerVersion = "1.3.61"
val slf4jVersion = "1.7.30"
val hopliteVersion = "1.3.8"

plugins {
    id("com.github.johnrengelman.shadow") version "6.1.0"
    kotlin("jvm") version "1.4.20"
}

buildscript {
    repositories {
        jcenter()
        mavenCentral()
    }
    dependencies {
        classpath("org.jetbrains.kotlin:kotlin-gradle-plugin:1.4.20")
    }
}

group = "com.vacegaming.james"

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
    implementation("org.jetbrains.kotlin:kotlin-reflect:$kotlinVersion")

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

    build {
        dependsOn(shadowJar)
    }

    named<ShadowJar>("shadowJar") {
        archiveVersion.set(implementationVersion)
        archiveFileName.set("james-musicbot-reworked.jar")
        manifest.attributes.apply {
            put("Main-Class", "com.vacegaming.james.musicbot.MusicBotKt")
            put("Implementation-Version", implementationVersion)
        }
    }
}

val compileKotlin: KotlinCompile by tasks
compileKotlin.kotlinOptions {
    languageVersion = "1.4"
}

task("stage") {
    dependsOn("build")
}
