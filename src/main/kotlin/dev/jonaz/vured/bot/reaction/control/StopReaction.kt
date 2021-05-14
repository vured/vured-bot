package dev.jonaz.vured.bot.reaction.control

import dev.jonaz.vured.bot.reaction.Reaction
import dev.jonaz.vured.bot.reaction.ReactionHandler
import dev.jonaz.vured.bot.reaction.ReactionMessageCase
import dev.jonaz.vured.bot.service.application.LogService
import dev.jonaz.vured.bot.service.discord.VoiceChannelService
import dev.jonaz.vured.bot.application.Translation
import dev.jonaz.vured.util.extensions.genericInject
import net.dv8tion.jda.api.entities.Member
import java.awt.Color

@Reaction(4, "U+23f9", ReactionMessageCase.STATIC)
class StopReaction: ReactionHandler {
    private val voiceChannelService by genericInject<VoiceChannelService>()
    private val logService by genericInject<LogService>()

    override fun execute(member: Member) {
        voiceChannelService.leave()
        logService.sendLog(
            title = Translation.LOG_STOPPED_TITLE,
            description = Translation.LOG_STOPPED_DESCRIPTION,
            member = member,
            color = Color.ORANGE
        )
    }
}
