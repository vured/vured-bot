package dev.jonaz.vured.bot.reaction.control

import dev.jonaz.vured.bot.reaction.Reaction
import dev.jonaz.vured.bot.reaction.ReactionHandler
import dev.jonaz.vured.bot.reaction.ReactionMessageCase
import dev.jonaz.vured.bot.service.music.MusicService
import dev.jonaz.vured.bot.service.discord.StaticMessageService
import dev.jonaz.vured.util.extensions.genericInject
import net.dv8tion.jda.api.entities.Member
import java.awt.Color

@Reaction(6, "U+2795", ReactionMessageCase.STATIC)
class VolumeUpReaction : ReactionHandler {
    private val staticMessageService by genericInject<StaticMessageService>()
    private val musicService by genericInject<MusicService>()

    override fun execute(member: Member) {
        val volume = musicService.getVolume()
        val audioPlayer = musicService.getAudioPlayer()

        if(audioPlayer.isPaused) {
            return
        }

        val newVolume = if (volume > 4) {
            volume + 5
        } else {
            volume + 1
        }

        musicService.setVolume(newVolume)

        staticMessageService.build(
            title = audioPlayer.playingTrack.info.title,
            description = audioPlayer.playingTrack.info.author,
            color = Color.GREEN,
            volume = newVolume
        ).also { staticMessageService.set(it) }
    }
}
