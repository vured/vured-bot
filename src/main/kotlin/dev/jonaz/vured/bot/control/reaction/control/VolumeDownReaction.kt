package dev.jonaz.vured.bot.control.reaction.control

import dev.jonaz.vured.bot.control.reaction.Reaction
import dev.jonaz.vured.bot.control.reaction.ReactionHandler
import dev.jonaz.vured.bot.control.ControlMessageCase
import dev.jonaz.vured.bot.service.music.MusicService
import dev.jonaz.vured.bot.service.discord.StaticMessageService
import dev.jonaz.vured.bot.service.web.PlayerService
import dev.jonaz.vured.bot.util.extensions.genericInject
import net.dv8tion.jda.api.entities.Member
import java.awt.Color

@Reaction(1, "U+1f508", ControlMessageCase.STATIC)
class VolumeDownReaction : ReactionHandler {
    private val staticMessageService by genericInject<StaticMessageService>()
    private val musicService by genericInject<MusicService>()
    private val playerService by genericInject<PlayerService>()

    override fun execute(member: Member) {
        val volume = musicService.getVolume()
        val audioPlayer = musicService.getAudioPlayer()

        val newVolume = if (volume > 5) {
            volume - 5
        } else {
            volume - 1
        }

        musicService.setVolume(newVolume)
        playerService.sendEvent(audioPlayer)
    }
}
