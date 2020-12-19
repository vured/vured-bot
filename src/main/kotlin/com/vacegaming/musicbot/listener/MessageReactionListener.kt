package com.vacegaming.musicbot.listener

import com.vacegaming.musicbot.service.MemberService
import com.vacegaming.musicbot.service.ReactionService
import com.vacegaming.musicbot.util.data.Config
import com.vacegaming.musicbot.util.koin.genericInject
import net.dv8tion.jda.api.events.message.guild.react.GuildMessageReactionAddEvent
import net.dv8tion.jda.api.hooks.ListenerAdapter

class MessageReactionListener : ListenerAdapter() {
    private val reactionService by genericInject<ReactionService>()
    private val memberService by genericInject<MemberService>()

    override fun onGuildMessageReactionAdd(event: GuildMessageReactionAddEvent) {
        if (event.user.isBot) {
            return
        }

        if (event.channel.idLong != Config.data.musicBotChannelID) {
            return
        }

        if (event.reactionEmote.isEmote) {
            event.reaction.removeReaction()
            return
        }

        if(memberService.isPermitted(event.member).not()) {
            event.reaction.removeReaction(event.user).queue()
            return
        }

        if(memberService.isInChannel(event.member).not()) {
            event.reaction.removeReaction().queue()
            return
        }

        event.reaction.removeReaction(event.user).queue()

        reactionService.execute(event.reactionEmote.asCodepoints, event.member)
    }
}
