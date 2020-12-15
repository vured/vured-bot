import org.jetbrains.kotlin.gradle.tasks.KotlinCompile
import com.github.jengelman.gradle.plugins.shadow.tasks.ShadowJar
import com.vacegaming.musicbot.gradle.Versions

plugins {
    kotlin("jvm") version "1.4.21"
    kotlin("kapt") version "1.4.21"
    id("com.github.johnrengelman.shadow") version "6.1.0"
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

group = "com.vacegaming.musicbot"

repositories {
    jcenter()
    mavenCentral()
}

dependencies {
    implementation(kotlin("stdlib-jdk8"))

    kapt("org.atteo.classindex", "classindex", Versions.CLASSINDEX)

    /**
     * Kotlin
     */
    implementation("org.jetbrains.kotlinx", "kotlinx-coroutines-core", Versions.KOTLIN_COROUTINES)
    implementation("org.jetbrains.kotlin", "kotlin-reflect", Versions.KOTLIN)

    /**
     * Discord
     */
    implementation("net.dv8tion", "JDA", Versions.JDA)
    implementation("com.sedmelluq", "lavaplayer", Versions.LAVAPLAYER)

    /**
     * Application
     */
    implementation("org.atteo.classindex", "classindex", Versions.CLASSINDEX)
    implementation("org.slf4j", "slf4j-simple", Versions.SLF4J)
    implementation("com.sksamuel.hoplite", "hoplite-core", Versions.HOPLITE)
    implementation("com.sksamuel.hoplite", "hoplite-json", Versions.HOPLITE)
}

tasks {
    compileKotlin {
        kotlinOptions.jvmTarget = "11"
    }

    build {
        dependsOn(shadowJar)
    }

    named<ShadowJar>("shadowJar") {
        manifest.attributes.apply {
            put("Main-Class", "com.vacegaming.musicbot.MusicBotKt")
            put("Implementation-Version", project.version)
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
