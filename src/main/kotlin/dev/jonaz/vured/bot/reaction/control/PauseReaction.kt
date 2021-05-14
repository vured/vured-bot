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
