package com.vacegaming.james.musicbot.util

import com.sksamuel.hoplite.ConfigLoader
import com.vacegaming.james.musicbot.model.BotConfigModel
import com.vacegaming.james.musicbot.util.environment.EnvironmentType
import com.vacegaming.james.musicbot.util.environment.EnvironmentManager

object ConfigManager {
    private val configLoader = ConfigLoader()

    lateinit var data: BotConfigModel

    fun setConfigFile() = when (EnvironmentManager.current) {
        EnvironmentType.DEV -> "/default.json"
        EnvironmentType.PROD -> "/production.json"
        EnvironmentType.DEFAULT -> "/default.json"
    }.apply {
        data = configLoader.loadConfigOrThrow(this)
    }.run {
        setBotToken()
    }

    private fun setBotToken() {
        if (EnvironmentManager.current == EnvironmentType.DEV) return

        data.botToken = EnvironmentManager.getEnvironmentVariable("botToken")
    }
}
