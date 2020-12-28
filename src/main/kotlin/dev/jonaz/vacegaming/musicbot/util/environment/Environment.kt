package dev.jonaz.vacegaming.musicbot.util.environment

enum class Environment {
    DEV {
        override val config: String = "/application-dev.conf"
    },

    PROD {
        override val config: String = "/application-prod.conf"
    };

    abstract val config: String
}
