package com.vacegaming.musicbot.listener

import com.vacegaming.musicbot.core.MemberManager
import com.vacegaming.musicbot.util.ConfigManager
import net.dv8tion.jda.api.events.message.guild.react.GuildMessageReactionAddEvent
import net.dv8tion.jda.api.hooks.ListenerAdapter

class MessageReactionListener : ListenerAdapter() {

    override fun onGuildMessageReactionAdd(event: GuildMessageReactionAddEvent) {
        val member = event.member
        val isBot = event.user.isBot
        val channelId = event.channel.idLong
        val reactionEmote = event.reactionEmote
        val musicChannelId = ConfigManager.data.musicBotChannelID

        when {
            isBot -> return
            channelId != musicChannelId -> return
            reactionEmote.isEmote -> return event.reaction.removeReaction(event.user).queue()
            MemberManager.isPermitted(member).not() -> return event.reaction.removeReaction(event.user).queue()

            MemberManager.isInChannel(member).not() -> return event.reaction.removeReaction(event.user).queue()
        }

        event.reaction.removeReaction(event.user).queue()
    }
}
