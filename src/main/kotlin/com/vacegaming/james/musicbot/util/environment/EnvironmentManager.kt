package com.vacegaming.james.musicbot.util.environment

import java.lang.IllegalArgumentException

object EnvironmentManager {
    var current: EnvironmentType = EnvironmentType.DEFAULT

    fun set() = System.getProperty("env")?.apply {
        current = when (this) {
            "dev" -> EnvironmentType.DEV
            "prod" -> EnvironmentType.PROD
            else -> EnvironmentType.DEFAULT
        }
    }

    fun getEnvironmentVariable(name: String) = System.getenv(name)
        ?: throw IllegalArgumentException("Environment variable ($name) is missing")
}
