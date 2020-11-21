package com.vacegaming.james.musicbot.listener

import com.vacegaming.james.musicbot.core.PermissionManager
import com.vacegaming.james.musicbot.util.ConfigManager
import net.dv8tion.jda.api.events.message.guild.react.GuildMessageReactionAddEvent
import net.dv8tion.jda.api.hooks.ListenerAdapter

class MessageReactionListener : ListenerAdapter() {

    override fun onGuildMessageReactionAdd(event: GuildMessageReactionAddEvent) {
        if (event.channel.idLong != ConfigManager.data.musicBotChannelID) return
        if (event.user.isBot) return

        event.reaction.removeReaction(event.user).queue()

        PermissionManager.isMemberPermitted(event.member).run {
            if (this.not()) return
        }
    }
}
