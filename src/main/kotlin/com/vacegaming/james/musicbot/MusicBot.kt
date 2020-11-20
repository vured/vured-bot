package com.vacegaming.james.musicbot

import com.vacegaming.james.musicbot.music.MusicChannel
import com.vacegaming.james.musicbot.util.DiscordClient
import com.vacegaming.james.musicbot.util.ConfigManager
import com.vacegaming.james.musicbot.util.environment.EnvironmentManager

fun main() {
    EnvironmentManager.set()
    ConfigManager.setConfigFile()
    DiscordClient.start()

    MusicChannel.createStaticMessage()
}
