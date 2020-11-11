import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    kotlin("jvm") version "1.4.10"
}
group = "com.vacegaming.james"
version = "1.0-SNAPSHOT"

repositories {
    jcenter()
    mavenCentral()
}

dependencies {
    implementation(kotlin("stdlib-jdk8"))
    implementation("net.dv8tion:JDA:4.2.0_214")
    implementation("org.slf4j:slf4j-simple:1.6.2")
    implementation("com.sksamuel.hoplite:hoplite-core:1.3.7")
    implementation("com.sksamuel.hoplite:hoplite-json:1.3.7")
    implementation("com.sedmelluq:lavaplayer:1.3.59")
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
