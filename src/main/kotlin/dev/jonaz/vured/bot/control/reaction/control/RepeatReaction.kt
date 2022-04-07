package dev.jonaz.vured.bot.control.reaction.control

import dev.jonaz.vured.bot.control.ControlMessageCase
import dev.jonaz.vured.bot.control.reaction.Reaction
import dev.jonaz.vured.bot.control.reaction.ReactionHandler
import dev.jonaz.vured.bot.service.music.MusicService
import dev.jonaz.vured.bot.util.extensions.genericInject
import net.dv8tion.jda.api.entities.Member

@Reaction(3, "U+1f501", ControlMessageCase.STATIC)
class RepeatReaction : ReactionHandler {
    private val musicService by genericInject<MusicService>()

    override fun execute(member: Member) {
        val isRepeating = musicService.getRepeatTrack()
        musicService.setRepeatTrack(!isRepeating)
    }
}
