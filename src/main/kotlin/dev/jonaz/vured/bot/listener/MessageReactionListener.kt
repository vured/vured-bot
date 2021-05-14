package dev.jonaz.vured.bot.listener

import dev.jonaz.vured.bot.service.application.ConfigService
import dev.jonaz.vured.bot.service.discord.MemberService
import dev.jonaz.vured.bot.service.discord.ReactionService
import dev.jonaz.vured.util.extensions.genericInject
import net.dv8tion.jda.api.events.message.guild.react.GuildMessageReactionAddEvent
import net.dv8tion.jda.api.hooks.ListenerAdapter

class MessageReactionListener : ListenerAdapter() {
    private val reactionService by genericInject<ReactionService>()
    private val memberService by genericInject<MemberService>()

    private val config by ConfigService

    override fun onGuildMessageReactionAdd(event: GuildMessageReactionAddEvent) {
        if (event.user.isBot) {
            return
        }

        if (event.channel.idLong != config.discord.musicChannel) {
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
