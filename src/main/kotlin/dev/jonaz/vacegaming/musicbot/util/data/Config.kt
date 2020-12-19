package dev.jonaz.vacegaming.musicbot.util.data

import com.sksamuel.hoplite.ConfigLoader
import dev.jonaz.vacegaming.musicbot.model.BotConfigModel
import dev.jonaz.vacegaming.musicbot.util.environment.Environment
import dev.jonaz.vacegaming.musicbot.util.environment.EnvironmentType

object Config {
    lateinit var data: BotConfigModel
    lateinit var botToken: String

    fun load() = when (Environment.type) {
        EnvironmentType.DEV -> "/development.json"
        EnvironmentType.PROD -> "/production.json"
    }.also {
        data = ConfigLoader().loadConfigOrThrow(it)
        botToken = Environment.getEnvironmentVariable("BOT_TOKEN")
    }
}
