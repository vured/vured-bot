package dev.jonaz.vured.bot.control.reaction.control

import dev.jonaz.vured.bot.control.reaction.Reaction
import dev.jonaz.vured.bot.control.reaction.ReactionHandler
import dev.jonaz.vured.bot.control.ControlMessageCase
import dev.jonaz.vured.bot.service.music.MusicService
import dev.jonaz.vured.bot.service.web.PlayerService
import dev.jonaz.vured.bot.util.extensions.genericInject
import net.dv8tion.jda.api.entities.Member

@Reaction(2, "U+1f50a", ControlMessageCase.STATIC)
class VolumeUpReaction : ReactionHandler {
    private val musicService by genericInject<MusicService>()
    private val playerService by genericInject<PlayerService>()

    override fun execute(member: Member) {
        val volume = musicService.getVolume()
        val audioPlayer = musicService.getAudioPlayer()

        val newVolume = if (volume > 4) {
            volume + 5
        } else {
            volume + 1
        }

        musicService.setVolume(newVolume)
        playerService.sendEvent(audioPlayer)
    }
}
