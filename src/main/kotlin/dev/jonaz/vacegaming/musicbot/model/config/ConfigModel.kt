package dev.jonaz.vacegaming.musicbot.model.config

import dev.jonaz.vured.util.environment.Environment

data class ConfigModel(
    val env: Environment,
    val discord: ConfigDiscordModel,
    val bot: ConfigBotModel
)
