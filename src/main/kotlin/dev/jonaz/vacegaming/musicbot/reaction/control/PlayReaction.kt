package dev.jonaz.vacegaming.musicbot.reaction.control

import dev.jonaz.vacegaming.musicbot.reaction.Reaction
import dev.jonaz.vacegaming.musicbot.reaction.ReactionHandler
import dev.jonaz.vacegaming.musicbot.reaction.ReactionMessageCase
import dev.jonaz.vacegaming.musicbot.service.application.LogService
import dev.jonaz.vacegaming.musicbot.service.music.MusicService
import dev.jonaz.vacegaming.musicbot.util.application.Translation
import dev.jonaz.vacegaming.musicbot.util.koin.genericInject
import net.dv8tion.jda.api.entities.Member
import java.awt.Color

@Reaction(1, "U+25b6", ReactionMessageCase.STATIC)
class PlayReaction: ReactionHandler {
    private val musicService by genericInject<MusicService>()
    private val logService by genericInject<LogService>()

    override fun execute(member: Member) {
        musicService.setResume()
        logService.sendLog(
            title = Translation.LOG_RESUMED_TITLE,
            description = Translation.LOG_RESUMED_DESCRIPTION,
            member = member,
            color = Color.CYAN
        )
    }
}
