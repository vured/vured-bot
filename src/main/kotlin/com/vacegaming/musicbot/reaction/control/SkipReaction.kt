package com.vacegaming.musicbot.reaction.control

import com.vacegaming.musicbot.reaction.Reaction
import com.vacegaming.musicbot.reaction.ReactionHandler
import com.vacegaming.musicbot.reaction.ReactionMessageCase
import com.vacegaming.musicbot.service.LogService
import com.vacegaming.musicbot.service.MusicService
import com.vacegaming.musicbot.util.data.Translation
import com.vacegaming.musicbot.util.koin.genericInject
import net.dv8tion.jda.api.entities.Member
import java.awt.Color

@Reaction(3, "U+23ed", ReactionMessageCase.STATIC)
class SkipReaction : ReactionHandler {
    private val musicService by genericInject<MusicService>()
    private val logService by genericInject<LogService>()

    override fun execute(member: Member) {
        musicService.nextTrack()
        musicService.setResume()
        logService.sendLog(
            title = Translation.LOG_SKIPPED_TITLE,
            description = Translation.LOG_SKIPPED_DESCRIPTION,
            member = member,
            color = Color.CYAN
        )
    }
}
