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

@Reaction(2, "U+23f8", ReactionMessageCase.STATIC)
class PauseReaction : ReactionHandler {
    private val musicService by genericInject<MusicService>()
    private val logService by genericInject<LogService>()

    override fun execute(member: Member) {
        musicService.setPause()
        logService.sendLog(
            title = Translation.LOG_PAUSED_TITLE,
            description = Translation.LOG_PAUSED_DESCRIPTION,
            member = member,
            color = Color.CYAN
        )
    }
}
