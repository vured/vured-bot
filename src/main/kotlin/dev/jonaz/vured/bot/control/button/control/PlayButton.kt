package dev.jonaz.vured.bot.control.button.control

import dev.jonaz.vured.bot.application.Translation
import dev.jonaz.vured.bot.control.ControlMessageCase
import dev.jonaz.vured.bot.control.button.Button
import dev.jonaz.vured.bot.control.button.ButtonHandler
import dev.jonaz.vured.bot.service.application.LogService
import dev.jonaz.vured.bot.service.music.MusicService
import dev.jonaz.vured.util.extensions.genericInject
import net.dv8tion.jda.api.events.interaction.ButtonClickEvent
import net.dv8tion.jda.api.interactions.components.ButtonStyle
import java.awt.Color

@Button(
    order = 1,
    identifier = "music-play",
    style = ButtonStyle.SECONDARY,
    messageCase = ControlMessageCase.STATIC,
    content = "Play"
)
class PlayButton : ButtonHandler {
    private val musicService by genericInject<MusicService>()
    private val logService by genericInject<LogService>()

    override fun execute(event: ButtonClickEvent) {
        musicService.setResume()
        logService.sendLog(
            title = Translation.LOG_RESUMED_TITLE,
            description = Translation.LOG_RESUMED_DESCRIPTION,
            member = event.member,
            color = Color.CYAN
        )
    }
}
