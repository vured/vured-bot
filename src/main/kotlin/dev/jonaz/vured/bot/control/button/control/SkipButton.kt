package dev.jonaz.vured.bot.control.button.control

import dev.jonaz.vured.bot.application.Translation
import dev.jonaz.vured.bot.control.ControlMessageCase
import dev.jonaz.vured.bot.control.button.Button
import dev.jonaz.vured.bot.control.button.ButtonHandler
import dev.jonaz.vured.bot.service.application.LogService
import dev.jonaz.vured.bot.service.music.MusicService
import dev.jonaz.vured.bot.util.extensions.genericInject
import net.dv8tion.jda.api.events.interaction.component.ButtonInteractionEvent
import net.dv8tion.jda.api.interactions.components.buttons.ButtonStyle
import java.awt.Color

@Button(
    order = 3,
    identifier = "music-skip",
    style = ButtonStyle.PRIMARY,
    messageCase = ControlMessageCase.STATIC,
    content = "Skip"
)
class SkipButton : ButtonHandler {
    private val musicService by genericInject<MusicService>()
    private val logService by genericInject<LogService>()

    override fun execute(event: ButtonInteractionEvent) {
        musicService.nextTrack()
        musicService.setResume()
        logService.sendLog(
            title = Translation.LOG_SKIPPED_TITLE,
            description = Translation.LOG_SKIPPED_DESCRIPTION,
            member = event.member,
            color = Color.CYAN
        )
    }
}
