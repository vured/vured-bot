package com.vacegaming.james.musicbot.util.environment

object EnvironmentManager {
    lateinit var current: Environment

    fun set(): String = System.getProperty("env").apply {
        current = when (this) {
            "dev" -> Environment.DEV
            "prod" -> Environment.PROD
            else -> Environment.DEFAULT
        }
    }
}
