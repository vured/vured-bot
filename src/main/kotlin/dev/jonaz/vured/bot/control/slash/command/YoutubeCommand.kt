package dev.jonaz.vured.bot.control.slash.command

import dev.jonaz.vured.bot.application.Translation
import dev.jonaz.vured.bot.control.slash.CommandHandler
import dev.jonaz.vured.bot.control.slash.SlashCommand
import dev.jonaz.vured.bot.service.music.MusicService
import dev.jonaz.vured.bot.util.extensions.genericInject
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent
import net.dv8tion.jda.api.interactions.commands.OptionType
import net.dv8tion.jda.api.requests.restaction.CommandCreateAction

@SlashCommand(
    name = "youtube",
    description = Translation.COMMAND_YOUTUBE_DESC
)
class YoutubeCommand : CommandHandler {
    private val musicService by genericInject<MusicService>()

    override fun upsertCommand(commandCreateAction: CommandCreateAction): CommandCreateAction {
        return commandCreateAction.addOption(
            OptionType.STRING,
            Translation.COMMAND_YOUTUBE_OPTION_NAME,
            Translation.COMMAND_YOUTUBE_OPTION_DESC,
            true
        )
    }

    override fun execute(event: SlashCommandInteractionEvent) {
        val query = event.getOption("query")?.asString

        query?.run {
            musicService.loadItem(event.member, "ytsearch:$this")
        }

        event.hook.sendMessage(Translation.COMMAND_YOUTUBE_RESPONSE).setEphemeral(true).queue()
    }
}
