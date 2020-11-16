package com.vacegaming.james.musicbot.util.config

import com.sksamuel.hoplite.ConfigLoader
import com.vacegaming.james.musicbot.model.BotConfigModel
import com.vacegaming.james.musicbot.util.environment.Environment
import com.vacegaming.james.musicbot.util.environment.EnvironmentManager

object ConfigManager {
    private val configLoader = ConfigLoader()

    lateinit var data: BotConfigModel

    fun setConfigFile() = when (EnvironmentManager.current) {
        Environment.DEV -> "/default.json"
        Environment.PROD -> "/production.json"
        Environment.DEFAULT -> "/default.json"
    }.apply {
        data = configLoader.loadConfigOrThrow(this)
    }
}
