package dev.jonaz.vured.bot.listener

import dev.jonaz.vured.bot.application.Translation
import dev.jonaz.vured.bot.service.application.ConfigService
import dev.jonaz.vured.bot.service.discord.ButtonService
import dev.jonaz.vured.bot.service.discord.MemberService
import dev.jonaz.vured.util.extensions.genericInject
import net.dv8tion.jda.api.events.interaction.ButtonClickEvent
import net.dv8tion.jda.api.hooks.ListenerAdapter

class ButtonClickListener : ListenerAdapter() {
    private val memberService by genericInject<MemberService>()
    private val buttonService by genericInject<ButtonService>()

    private val config by ConfigService

    override fun onButtonClick(event: ButtonClickEvent) {
        if (event.channel.idLong != config.discord.musicChannel) {
            return
        }

        if (memberService.isPermitted(event.member).not()) {
            event.reply(Translation.NO_PERMISSIONS)
                .setEphemeral(true)
                .complete()
            return
        }

        if (memberService.isInChannel(event.member).not()) {
            event.reply(Translation.NOT_SAME_VOICE_CHANNEL)
                .setEphemeral(true)
                .complete()
            return
        }

        buttonService.execute(event)

        event.deferEdit().queue()
    }
}
