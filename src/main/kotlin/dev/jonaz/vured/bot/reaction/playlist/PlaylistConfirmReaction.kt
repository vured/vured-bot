package dev.jonaz.vured.bot.reaction.playlist

import dev.jonaz.vured.bot.reaction.Reaction
import dev.jonaz.vured.bot.reaction.ReactionHandler
import dev.jonaz.vured.bot.reaction.ReactionMessageCase
import dev.jonaz.vured.bot.service.music.PlaylistService
import dev.jonaz.vured.util.extensions.genericInject
import net.dv8tion.jda.api.entities.Member

@Reaction(1, "U+2714", ReactionMessageCase.PLAYLIST)
class PlaylistConfirmReaction : ReactionHandler {
    private val playlistService by genericInject<PlaylistService>()

    override fun execute(member: Member) {
        playlistService.questionAnswers.offer(Pair(true, member))
    }
}
