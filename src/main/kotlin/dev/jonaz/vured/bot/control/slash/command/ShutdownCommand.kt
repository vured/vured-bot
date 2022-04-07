package dev.jonaz.vured.bot.control.slash.command

import dev.jonaz.vured.bot.application.Translation
import dev.jonaz.vured.bot.control.slash.CommandHandler
import dev.jonaz.vured.bot.control.slash.SlashCommand
import dev.jonaz.vured.bot.service.discord.MemberService
import dev.jonaz.vured.bot.util.extensions.genericInject
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent
import net.dv8tion.jda.api.requests.restaction.CommandCreateAction
import kotlin.system.exitProcess

@SlashCommand(
    name = "shutdown",
    description = Translation.COMMAND_SHUTDOWN_DESC
)
class ShutdownCommand : CommandHandler {
    private val memberService by genericInject<MemberService>()

    override fun upsertCommand(commandCreateAction: CommandCreateAction): CommandCreateAction {
        return commandCreateAction
    }

    override fun execute(event: SlashCommandInteractionEvent) {
        if (memberService.isPermitted(event.member).not()) {
            event.hook.sendMessage(Translation.NO_PERMISSIONS).complete()
            return
        }

        event.hook.sendMessage(":thumbsup:").setEphemeral(true).complete()

        exitProcess(0)
    }
}
