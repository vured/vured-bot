package com.vacegaming.musicbot.reaction.control

import com.vacegaming.musicbot.reaction.Reaction
import com.vacegaming.musicbot.reaction.ReactionHandler
import com.vacegaming.musicbot.reaction.ReactionMessageCase
import com.vacegaming.musicbot.service.LogService
import com.vacegaming.musicbot.service.VoiceChannelService
import com.vacegaming.musicbot.util.data.Translation
import com.vacegaming.musicbot.util.koin.genericInject
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
