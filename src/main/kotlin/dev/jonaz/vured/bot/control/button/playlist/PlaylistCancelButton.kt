package dev.jonaz.vured.bot.control.button.playlist

import dev.jonaz.vured.bot.control.ControlMessageCase
import dev.jonaz.vured.bot.control.button.Button
import dev.jonaz.vured.bot.control.button.ButtonHandler
import dev.jonaz.vured.bot.service.music.PlaylistService
import dev.jonaz.vured.util.extensions.genericInject
import net.dv8tion.jda.api.events.interaction.ButtonClickEvent
import net.dv8tion.jda.api.interactions.components.ButtonStyle

@Button(
    order = 1,
    identifier = "playlist-cancel",
    style = ButtonStyle.DANGER,
    messageCase = ControlMessageCase.PLAYLIST,
    content = "No"
)
class PlaylistCancelButton : ButtonHandler {
    private val playlistService by genericInject<PlaylistService>()

    override fun execute(event: ButtonClickEvent) {
        val member = event.member ?: return
        val answer = Pair(false, member)

        playlistService.questionAnswers.trySend(answer)
    }
}
