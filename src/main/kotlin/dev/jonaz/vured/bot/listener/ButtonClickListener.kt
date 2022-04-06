package dev.jonaz.vured.bot.listener

import dev.jonaz.vured.bot.application.Translation
import dev.jonaz.vured.bot.service.application.ConfigService
import dev.jonaz.vured.bot.service.discord.ButtonService
import dev.jonaz.vured.bot.service.discord.MemberService
import dev.jonaz.vured.bot.util.extensions.genericInject
import net.dv8tion.jda.api.events.interaction.ButtonClickEvent
import net.dv8tion.jda.api.hooks.ListenerAdapter

class ButtonClickListener : ListenerAdapter() {
    private val memberService by genericInject<MemberService>()
    private val buttonService by genericInject<ButtonService>()

    private val config by ConfigService

    override fun onButtonClick(event: ButtonClickEvent) {
        event.deferEdit().queue()

        if (event.channel.idLong != config.discord.musicChannel) {
            return
        }

        if (memberService.isPermitted(event.member).not()) {
            event.hook.sendMessage(Translation.NO_PERMISSIONS)
                .setEphemeral(true)
                .complete()
            return
        }

        if (memberService.isInChannel(event.member).not()) {
            event.hook.sendMessage(Translation.NOT_SAME_VOICE_CHANNEL)
                .setEphemeral(true)
                .complete()
            return
        }

        buttonService.execute(event)
    }
}
