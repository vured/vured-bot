package com.vacegaming.musicbot.util

import com.sksamuel.hoplite.ConfigLoader
import com.vacegaming.musicbot.model.BotConfigModel
import com.vacegaming.musicbot.util.environment.EnvironmentManager
import com.vacegaming.musicbot.util.environment.EnvironmentType

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

        data.botToken = EnvironmentManager.getEnvironmentVariable("BOT_TOKEN")
    }
}
