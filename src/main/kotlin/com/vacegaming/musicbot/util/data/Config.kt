package com.vacegaming.musicbot.util.data

import com.sksamuel.hoplite.ConfigLoader
import com.vacegaming.musicbot.model.BotConfigModel
import com.vacegaming.musicbot.util.environment.EnvironmentManager
import com.vacegaming.musicbot.util.environment.EnvironmentType

object Config {
    lateinit var data: BotConfigModel
    lateinit var botToken: String

    fun load() = when (EnvironmentManager.current) {
        EnvironmentType.DEV -> "/default.json"
        EnvironmentType.PROD -> "/production.json"
        EnvironmentType.DEFAULT -> "/default.json"
    }.apply {
        data = ConfigLoader().loadConfigOrThrow(this)
    }.also {
        botToken = EnvironmentManager.getEnvironmentVariable("BOT_TOKEN")
    }
}
