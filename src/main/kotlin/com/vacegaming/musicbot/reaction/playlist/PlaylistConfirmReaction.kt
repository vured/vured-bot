package com.vacegaming.musicbot.reaction.playlist

import com.vacegaming.musicbot.reaction.Reaction
import com.vacegaming.musicbot.reaction.ReactionHandler
import com.vacegaming.musicbot.service.PlaylistService
import com.vacegaming.musicbot.util.koin.genericInject
import net.dv8tion.jda.api.entities.Member

@Reaction(1, "U+2714")
class PlaylistConfirmReaction : ReactionHandler {
    private val playlistService by genericInject<PlaylistService>()

    override fun execute(member: Member) {
        playlistService.questionAnswers.offer(Pair(true, member))
    }
}
