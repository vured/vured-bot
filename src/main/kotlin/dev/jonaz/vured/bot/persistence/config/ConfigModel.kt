package dev.jonaz.vured.bot.persistence.config

import dev.jonaz.vured.bot.util.environment.Environment

data class ConfigModel(
    val env: Environment,
    val discord: ConfigDiscordModel,
    val bot: ConfigBotModel,
    val port: Int
)
