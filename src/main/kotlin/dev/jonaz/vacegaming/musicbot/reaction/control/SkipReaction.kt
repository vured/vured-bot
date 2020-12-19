package dev.jonaz.vacegaming.musicbot.reaction.control

import dev.jonaz.vacegaming.musicbot.reaction.Reaction
import dev.jonaz.vacegaming.musicbot.reaction.ReactionHandler
import dev.jonaz.vacegaming.musicbot.reaction.ReactionMessageCase
import dev.jonaz.vacegaming.musicbot.service.LogService
import dev.jonaz.vacegaming.musicbot.service.MusicService
import dev.jonaz.vacegaming.musicbot.util.data.Translation
import dev.jonaz.vacegaming.musicbot.util.koin.genericInject
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
