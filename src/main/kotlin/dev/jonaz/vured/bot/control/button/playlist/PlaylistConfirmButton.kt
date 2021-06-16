package dev.jonaz.vured.bot.control.button.playlist

import dev.jonaz.vured.bot.application.Translation
import dev.jonaz.vured.bot.control.ControlMessageCase
import dev.jonaz.vured.bot.control.button.Button
import dev.jonaz.vured.bot.control.button.ButtonHandler
import dev.jonaz.vured.bot.service.music.PlaylistService
import dev.jonaz.vured.util.extensions.genericInject
import net.dv8tion.jda.api.events.interaction.ButtonClickEvent
import net.dv8tion.jda.api.interactions.components.ButtonStyle

@Button(
    order = 1,
    identifier = "playlist-confirm",
    style = ButtonStyle.SUCCESS,
    messageCase = ControlMessageCase.PLAYLIST,
    content = Translation.PLAYLIST_IMPORT_BUTTON_CONFIRM
)
class PlaylistConfirmButton : ButtonHandler {
    private val playlistService by genericInject<PlaylistService>()

    override fun execute(event: ButtonClickEvent) {
        val member = event.member ?: return
        val answer = Pair(true, member)

        playlistService.questionAnswers.trySend(answer)
    }
}
