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
    order = 2,
    identifier = "music-pause",
    style = ButtonStyle.SECONDARY,
    messageCase = ControlMessageCase.STATIC,
    content = "Pause"
)
class PauseButton : ButtonHandler {
    private val musicService by genericInject<MusicService>()
    private val logService by genericInject<LogService>()

    override fun execute(event: ButtonClickEvent) {
        musicService.setPause()
        logService.sendLog(
            title = Translation.LOG_PAUSED_TITLE,
            description = Translation.LOG_PAUSED_DESCRIPTION,
            member = event.member,
            color = Color.CYAN
        )
    }
}
