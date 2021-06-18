import org.jetbrains.kotlin.gradle.tasks.KotlinCompile
import com.github.jengelman.gradle.plugins.shadow.tasks.ShadowJar
import dev.jonaz.vured.bot.gradle.Versions

plugins {
    kotlin("jvm") version "1.5.0"
    kotlin("kapt") version "1.5.0"
    kotlin("plugin.serialization") version "1.5.0"
    id("com.github.johnrengelman.shadow") version "6.1.0"
}

buildscript {
    repositories {
        mavenCentral()
    }
    dependencies {
        classpath("org.jetbrains.kotlin:kotlin-gradle-plugin:1.5.0")
    }
}

group = "dev.jonaz.vured.bot"

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
    implementation("dev.jonaz.vured.util", "vured-util", Versions.VURED_UTILS)
    implementation("io.insert-koin", "koin-core", Versions.KOIN)
    implementation("org.atteo.classindex", "classindex", Versions.CLASSINDEX)
    implementation("com.sksamuel.hoplite", "hoplite-core", Versions.HOPLITE)
    implementation("com.sksamuel.hoplite", "hoplite-hocon", Versions.HOPLITE)

    /** Ktor **/
    implementation("io.ktor", "ktor-server-core", Versions.KTOR)
    implementation("io.ktor", "ktor-server-netty", Versions.KTOR)
    implementation("io.ktor", "ktor-auth", Versions.KTOR)
    implementation("io.ktor", "ktor-auth-jwt", Versions.KTOR)
    implementation("io.ktor", "ktor-serialization", Versions.KTOR)
}

tasks {
    compileKotlin {
        kotlinOptions.jvmTarget = "11"
    }

    build {
        dependsOn(shadowJar)
    }

    named<ShadowJar>("shadowJar") {
        archiveFileName.set("vured-bot.jar")
        manifest.attributes.apply {
            put("Main-Class", "dev.jonaz.vured.bot.VuredKt")
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
