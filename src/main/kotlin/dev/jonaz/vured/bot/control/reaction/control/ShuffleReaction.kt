package dev.jonaz.vured.bot.control.reaction.control

import dev.jonaz.vured.bot.control.ControlMessageCase
import dev.jonaz.vured.bot.control.reaction.Reaction
import dev.jonaz.vured.bot.control.reaction.ReactionHandler
import dev.jonaz.vured.bot.service.music.MusicService
import dev.jonaz.vured.bot.util.extensions.genericInject
import net.dv8tion.jda.api.entities.Member

@Reaction(4, "U+1f500", ControlMessageCase.STATIC)
class ShuffleReaction : ReactionHandler {
    private val musicService by genericInject<MusicService>()

    override fun execute(member: Member) {
        val isShuffle = musicService.getShuffleTrack()
        musicService.setShuffleTrack(!isShuffle)
    }
}
