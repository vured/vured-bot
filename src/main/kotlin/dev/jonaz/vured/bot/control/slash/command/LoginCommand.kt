package dev.jonaz.vured.bot.control.slash.command

import dev.jonaz.vured.bot.application.Translation
import dev.jonaz.vured.bot.control.slash.CommandHandler
import dev.jonaz.vured.bot.control.slash.SlashCommand
import dev.jonaz.vured.bot.service.discord.MemberService
import dev.jonaz.vured.bot.service.web.JwtService
import dev.jonaz.vured.bot.web.authentication.UserPrincipal
import dev.jonaz.vured.util.extensions.genericInject
import net.dv8tion.jda.api.EmbedBuilder
import net.dv8tion.jda.api.events.interaction.SlashCommandEvent
import net.dv8tion.jda.api.requests.restaction.CommandCreateAction
import java.awt.Color

@SlashCommand(
    name = "login",
    description = Translation.COMMAND_LOGIN_DESC
)
class LoginCommand : CommandHandler {
    private val memberService by genericInject<MemberService>()
    private val jwtService by genericInject<JwtService>()

    override fun upsertCommand(commandCreateAction: CommandCreateAction): CommandCreateAction {
        return commandCreateAction
    }

    override fun execute(event: SlashCommandEvent) {
        if (memberService.isPermitted(event.member).not()) {
            event.hook.sendMessage(Translation.NO_PERMISSIONS).complete()
            return
        }

        val token = UserPrincipal(
            discord = event.member?.idLong ?: return,
            name = event.member?.effectiveName ?: return
        ).run { jwtService.createToken(this) }

        val message = EmbedBuilder().apply {
            this.setColor(Color.GREEN)

            this.setTitle("Login created")
            this.setDescription("Go to https://xxx.tld and enter the token.")

            this.addField("Token", "||$token||", false)
        }.run { this.build() }

        event.hook.sendMessageEmbeds(message).queue()
    }
}
