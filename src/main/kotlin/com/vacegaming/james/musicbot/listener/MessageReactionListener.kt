package com.vacegaming.james.musicbot.listener

import com.vacegaming.james.musicbot.core.MemberManager
import com.vacegaming.james.musicbot.core.reaction.*
import com.vacegaming.james.musicbot.util.ConfigManager
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
            reactionEmote.asCodepoints == RadioReaction.emote -> RadioReaction.execute(member)
            MemberManager.isInChannel(member).not() -> return event.reaction.removeReaction(event.user).queue()
        }

        event.reaction.removeReaction(event.user).queue()

        when (reactionEmote.asCodepoints) {
            PlayReaction.emote -> PlayReaction.execute(member)
            PauseReaction.emote -> PauseReaction.execute(member)
            NextReaction.emote -> NextReaction.execute(member)
            StopReaction.emote -> StopReaction.execute(member)
            VolumeDownReaction.emote -> VolumeDownReaction.execute()
            VolumeUpReaction.emote -> VolumeUpReaction.execute()
            ConfirmReaction.emote -> ConfirmReaction.execute(member)
            CancelReaction.emote -> CancelReaction.execute(member)
        }
    }
}
