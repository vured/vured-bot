package com.vacegaming.james.musicbot.listener

import com.vacegaming.james.musicbot.util.ConfigManager
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent
import net.dv8tion.jda.api.hooks.ListenerAdapter

object GuildMessageReceivedLister : ListenerAdapter() {

    override fun onGuildMessageReceived(event: GuildMessageReceivedEvent) {
        if (event.message.channel.idLong != ConfigManager.data.musicBotChannelID) return
    }
}
