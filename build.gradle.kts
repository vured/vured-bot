import org.jetbrains.kotlin.gradle.tasks.KotlinCompile
import com.github.jengelman.gradle.plugins.shadow.tasks.ShadowJar
import dev.jonaz.vacegaming.musicbot.gradle.Versions

plugins {
    kotlin("jvm") version "1.4.32"
    kotlin("kapt") version "1.4.32"
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
    mavenCentral()
    maven("https://m2.dv8tion.net/releases")
}

dependencies {
    implementation(kotlin("stdlib-jdk8"))

    kapt("org.atteo.classindex", "classindex", Versions.CLASSINDEX)

    /** Kotlin **/
    implementation("org.jetbrains.kotlinx", "kotlinx-coroutines-core", Versions.KOTLIN_COROUTINES)
    implementation("org.jetbrains.kotlin", "kotlin-reflect", Versions.KOTLIN)

    /** Discord **/
    implementation("net.dv8tion", "JDA", Versions.JDA)
    implementation("com.sedmelluq", "lavaplayer", Versions.LAVAPLAYER)

    /** Logging **/
    implementation("ch.qos.logback", "logback-classic", Versions.LOGBACK)
    implementation("io.sentry", "sentry-logback", Versions.SENTRY)

    /** Application **/
    implementation("io.insert-koin", "koin-core", Versions.KOIN)
    implementation("org.atteo.classindex", "classindex", Versions.CLASSINDEX)
    implementation("com.sksamuel.hoplite", "hoplite-core", Versions.HOPLITE)
    implementation("com.sksamuel.hoplite", "hoplite-hocon", Versions.HOPLITE)
}

tasks {
    compileKotlin {
        kotlinOptions.jvmTarget = "11"
    }

    build {
        dependsOn(shadowJar)
    }

    named<ShadowJar>("shadowJar") {
        archiveFileName.set("vacegaming-musicbot.jar")
        manifest.attributes.apply {
            put("Main-Class", "dev.jonaz.vacegaming.musicbot.MusicBotKt")
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
