package dev.jonaz.vacegaming.musicbot.listener

import dev.jonaz.vacegaming.musicbot.service.MemberService
import dev.jonaz.vacegaming.musicbot.service.ReactionService
import dev.jonaz.vacegaming.musicbot.util.data.Config
import dev.jonaz.vacegaming.musicbot.util.koin.genericInject
import net.dv8tion.jda.api.events.message.guild.react.GuildMessageReactionAddEvent
import net.dv8tion.jda.api.hooks.ListenerAdapter

class MessageReactionListener : ListenerAdapter() {
    private val reactionService by genericInject<ReactionService>()
    private val memberService by genericInject<MemberService>()

    override fun onGuildMessageReactionAdd(event: GuildMessageReactionAddEvent) {
        if (event.user.isBot) {
            return
        }

        if (event.channel.idLong != Config.musicChannel) {
            return
        }

        if (event.reactionEmote.isEmote) {
            event.reaction.removeReaction(event.member.user).queue()
            return
        }

        if(memberService.isPermitted(event.member).not()) {
            event.reaction.removeReaction(event.member.user).queue()
            return
        }

        if(memberService.isInChannel(event.member).not()) {
            event.reaction.removeReaction(event.member.user).queue()
            return
        }

        event.reaction.removeReaction(event.member.user).queue()

        reactionService.execute(event.reactionEmote.asCodepoints, event.member)
    }
}
