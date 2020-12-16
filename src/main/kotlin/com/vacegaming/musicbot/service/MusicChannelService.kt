package com.vacegaming.musicbot.service

import com.vacegaming.musicbot.util.ConfigManager
import com.vacegaming.musicbot.util.DiscordClient
import net.dv8tion.jda.api.entities.TextChannel

class MusicChannelService {

    fun getTextChannel(): TextChannel? {
        return DiscordClient.jda.getTextChannelById(
            ConfigManager.data.musicBotChannelID
        )
    }
}
