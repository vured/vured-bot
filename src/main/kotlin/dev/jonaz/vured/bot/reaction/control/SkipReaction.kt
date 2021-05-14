package dev.jonaz.vured.bot.reaction.control

import dev.jonaz.vured.bot.reaction.Reaction
import dev.jonaz.vured.bot.reaction.ReactionHandler
import dev.jonaz.vured.bot.reaction.ReactionMessageCase
import dev.jonaz.vured.bot.service.application.LogService
import dev.jonaz.vured.bot.service.music.MusicService
import dev.jonaz.vured.bot.application.Translation
import dev.jonaz.vured.util.extensions.genericInject
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
