package com.vacegaming.musicbot

import com.vacegaming.musicbot.core.ChannelManager
import com.vacegaming.musicbot.util.ConfigManager
import com.vacegaming.musicbot.util.DiscordClient
import com.vacegaming.musicbot.util.environment.EnvironmentManager
import kotlinx.coroutines.runBlocking

fun main() = runBlocking {
    EnvironmentManager.set()
    ConfigManager.setConfigFile()
    DiscordClient.start()

    ChannelManager.clearMessages()
    ChannelManager.createStaticMessage()

    ChannelManager.sendLog(
        "Musikbot gestartet",
        "Version ${this::class.java.`package`.implementationVersion}"
    )
}

