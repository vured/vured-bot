package dev.jonaz.vured.bot.reaction.playlist

import dev.jonaz.vured.bot.reaction.Reaction
import dev.jonaz.vured.bot.reaction.ReactionHandler
import dev.jonaz.vured.bot.reaction.ReactionMessageCase
import dev.jonaz.vured.bot.service.music.PlaylistService
import dev.jonaz.vured.util.extensions.genericInject
import net.dv8tion.jda.api.entities.Member

@Reaction(2, "U+274c", ReactionMessageCase.PLAYLIST)
class PlaylistCancelReaction : ReactionHandler {
    private val playlistService by genericInject<PlaylistService>()

    override fun execute(member: Member) {
        playlistService.questionAnswers.offer(Pair(false, member))
    }
}
