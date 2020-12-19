package com.vacegaming.musicbot.reaction.control

import com.vacegaming.musicbot.reaction.Reaction
import com.vacegaming.musicbot.reaction.ReactionHandler
import com.vacegaming.musicbot.reaction.ReactionMessageCase
import com.vacegaming.musicbot.service.MusicService
import com.vacegaming.musicbot.service.StaticMessageService
import com.vacegaming.musicbot.util.koin.genericInject
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
            description = null,
            color = Color.GREEN,
            volume = newVolume
        ).also { staticMessageService.set(it) }
    }
}
