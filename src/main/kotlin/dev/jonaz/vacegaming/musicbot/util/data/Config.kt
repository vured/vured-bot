package dev.jonaz.vacegaming.musicbot.util.data

import com.sksamuel.hoplite.ConfigLoader
import dev.jonaz.vacegaming.musicbot.model.BotConfigModel
import dev.jonaz.vacegaming.musicbot.util.environment.Environment
import dev.jonaz.vacegaming.musicbot.util.environment.EnvironmentType

object Config {
    lateinit var data: BotConfigModel

    /** Discord bot token */
    lateinit var botToken: String

    /** Role id that can access the bot */
    var role: Long = 0

    /** Guild id */
    var guild: Long = 0

    /** Text-Channel id for logs */
    var logChannel: Long = 0

    /** Text-Channel id for the music bot */
    var musicChannel: Long = 0

    fun load() = when (Environment.type) {
        EnvironmentType.DEV -> "/development.json"
        EnvironmentType.PROD -> "/production.json"
    }.also {
        data = ConfigLoader().loadConfigOrThrow(it)
        botToken = Environment.getEnvironmentVariable("BOT_TOKEN")

        role = Environment.getEnvironmentVariable("ROLE").toLong()
        guild = Environment.getEnvironmentVariable("GUILD").toLong()
        logChannel = Environment.getEnvironmentVariable("LOG_CHANNEL").toLong()
        musicChannel = Environment.getEnvironmentVariable("MUSIC_CHANNEL").toLong()
    }
}
