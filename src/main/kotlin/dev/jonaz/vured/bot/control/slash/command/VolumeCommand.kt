package dev.jonaz.vured.bot.control.slash.command

import dev.jonaz.vured.bot.application.Translation
import dev.jonaz.vured.bot.control.slash.CommandHandler
import dev.jonaz.vured.bot.control.slash.SlashCommand
import dev.jonaz.vured.bot.service.discord.MemberService
import dev.jonaz.vured.bot.service.discord.StaticMessageService
import dev.jonaz.vured.bot.service.music.MusicService
import dev.jonaz.vured.bot.util.extensions.genericInject
import net.dv8tion.jda.api.events.interaction.SlashCommandEvent
import net.dv8tion.jda.api.interactions.commands.OptionType
import net.dv8tion.jda.api.requests.restaction.CommandCreateAction
import java.awt.Color

@SlashCommand(
    name = "volume",
    description = Translation.COMMAND_VOLUME_DESC
)
class VolumeCommand : CommandHandler {
    private val staticMessageService by genericInject<StaticMessageService>()
    private val musicService by genericInject<MusicService>()
    private val memberService by genericInject<MemberService>()

    override fun upsertCommand(commandCreateAction: CommandCreateAction): CommandCreateAction {
        return commandCreateAction.addOption(
            OptionType.INTEGER,
            Translation.COMMAND_VOLUME_OPTION_NAME,
            Translation.COMMAND_VOLUME_OPTION_DESC,
            true
        )
    }

    override fun execute(event: SlashCommandEvent) {
        if (memberService.isInChannel(event.member).not()) {
            event.hook.sendMessage(Translation.NO_PERMISSIONS).complete()
            return
        }

        val audioPlayer = musicService.getAudioPlayer()
        val newVolume = event.getOption("percent")?.asLong?.toInt()

        newVolume?.run {
            musicService.setVolume(this)
        }

        event.hook.sendMessage(Translation.COMMAND_VOLUME_RESPONSE).setEphemeral(true).queue()
    }
}
