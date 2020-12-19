package dev.jonaz.vacegaming.musicbot.util.environment

import dev.jonaz.vacegaming.musicbot.util.data.Translation

object Environment {
    lateinit var type: EnvironmentType

    fun set(): String {
        type = System.getProperty("env").let {
            when (it) {
                "dev" -> EnvironmentType.DEV
                else -> EnvironmentType.PROD
            }
        }

        return if (type == EnvironmentType.DEV) {
            Translation.LOG_APPLICATION_STARTED_DEVELOPMENT
        } else {
            Translation.LOG_APPLICATION_STARTED_PRODUCTION.replace("%v", getImplementationVersion() ?: "")
        }
    }

    fun getEnvironmentVariable(name: String) = System.getenv(name)
        ?: throw IllegalArgumentException("Environment variable ($name) is missing")

    private fun getImplementationVersion(): String? {
        return this::class.java.`package`.implementationVersion
    }
}
