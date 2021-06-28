package dev.jonaz.vured.bot.control.button.control

import dev.jonaz.vured.bot.application.Translation
import dev.jonaz.vured.bot.control.ControlMessageCase
import dev.jonaz.vured.bot.control.button.Button
import dev.jonaz.vured.bot.control.button.ButtonHandler
import dev.jonaz.vured.bot.service.application.LogService
import dev.jonaz.vured.bot.service.discord.VoiceChannelService
import dev.jonaz.vured.bot.service.music.MusicService
import dev.jonaz.vured.bot.service.web.PlayerService
import dev.jonaz.vured.util.extensions.genericInject
import net.dv8tion.jda.api.events.interaction.ButtonClickEvent
import net.dv8tion.jda.api.interactions.components.ButtonStyle
import java.awt.Color

@Button(
    order = 4,
    identifier = "music-stop",
    style = ButtonStyle.DANGER,
    messageCase = ControlMessageCase.STATIC,
    content = "Stop"
)
class StopButton : ButtonHandler {
    private val voiceChannelService by genericInject<VoiceChannelService>()
    private val logService by genericInject<LogService>()
    private val musicService by genericInject<MusicService>()
    private val playerService by genericInject<PlayerService>()

    override fun execute(event: ButtonClickEvent) {
        voiceChannelService.leave()
        logService.sendLog(
            title = Translation.LOG_STOPPED_TITLE,
            description = Translation.LOG_STOPPED_DESCRIPTION,
            member = event.member,
            color = Color.ORANGE
        )

        musicService.getAudioPlayer().run {
            playerService.sendEvent(this)
        }
    }
}
