package dev.jonaz.vured.bot.listener

import dev.jonaz.vured.bot.application.Translation
import dev.jonaz.vured.bot.service.application.ConfigService
import dev.jonaz.vured.bot.service.discord.MemberService
import dev.jonaz.vured.bot.service.discord.SlashCommandService
import dev.jonaz.vured.bot.util.extensions.genericInject
import net.dv8tion.jda.api.events.interaction.SlashCommandEvent
import net.dv8tion.jda.api.hooks.ListenerAdapter

class SlashCommandListener : ListenerAdapter() {
    private val memberService by genericInject<MemberService>()
    private val slashCommandService by genericInject<SlashCommandService>()

    override fun onSlashCommand(event: SlashCommandEvent) {
        event.deferReply(true).queue()

        if (memberService.isPermitted(event.member).not()) {
            event.hook.sendMessage(Translation.NO_PERMISSIONS).complete()
            return
        }

        slashCommandService.execute(event)
    }
}
