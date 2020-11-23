package com.vacegaming.james.musicbot

import com.vacegaming.james.musicbot.core.ChannelManager
import com.vacegaming.james.musicbot.util.DiscordClient
import com.vacegaming.james.musicbot.util.ConfigManager
import com.vacegaming.james.musicbot.util.environment.EnvironmentManager
import kotlinx.coroutines.async
import kotlinx.coroutines.runBlocking

fun main() = runBlocking {
    EnvironmentManager.set()
    ConfigManager.setConfigFile()
    DiscordClient.start()

    ChannelManager.clearMessages()
    ChannelManager.createStaticMessage()
}
