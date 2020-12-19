package dev.jonaz.vacegaming.musicbot.reaction.control

import dev.jonaz.vacegaming.musicbot.reaction.Reaction
import dev.jonaz.vacegaming.musicbot.reaction.ReactionHandler
import dev.jonaz.vacegaming.musicbot.reaction.ReactionMessageCase
import dev.jonaz.vacegaming.musicbot.service.LogService
import dev.jonaz.vacegaming.musicbot.service.VoiceChannelService
import dev.jonaz.vacegaming.musicbot.util.data.Translation
import dev.jonaz.vacegaming.musicbot.util.koin.genericInject
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
