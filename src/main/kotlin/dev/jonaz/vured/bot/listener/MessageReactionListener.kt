package dev.jonaz.vured.bot.listener

import dev.jonaz.vured.bot.service.application.ConfigService
import dev.jonaz.vured.bot.service.discord.MemberService
import dev.jonaz.vured.bot.service.discord.ReactionService
import dev.jonaz.vured.bot.util.extensions.genericInject
import net.dv8tion.jda.api.events.message.react.MessageReactionAddEvent
import net.dv8tion.jda.api.hooks.ListenerAdapter
import net.dv8tion.jda.api.entities.emoji.Emoji.Type

class MessageReactionListener : ListenerAdapter() {
    private val reactionService by genericInject<ReactionService>()
    private val memberService by genericInject<MemberService>()

    private val config by ConfigService

    override fun onMessageReactionAdd(event: MessageReactionAddEvent) {
        if (event.user?.isBot == true) {
            return
        }

        if (event.channel.idLong != config.discord.musicChannel) {
            return
        }

        if (event.emoji.getType() == Type.CUSTOM) {
            event.user?.let { event.reaction.removeReaction(it).queue() }
            return
        }

        if (memberService.isPermitted(event.member).not()) {
            event.user?.let { event.reaction.removeReaction(it).queue() }
            return
        }

        if (memberService.isInChannel(event.member).not()) {
            event.user?.let { event.reaction.removeReaction(it).queue() }
            return
        }

        event.user?.let { event.reaction.removeReaction(it).queue() }

        event.member?.let { reactionService.execute(event.emoji.asUnicode().getAsCodepoints(), it) }
    }
}
