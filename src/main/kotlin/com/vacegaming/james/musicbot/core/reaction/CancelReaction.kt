package com.vacegaming.james.musicbot.core.reaction

import com.vacegaming.james.musicbot.core.music.PlaylistManager
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import net.dv8tion.jda.api.entities.Member

object CancelReaction {
    const val emote = "U+274c"

    fun execute(member: Member) = GlobalScope.launch {
        PlaylistManager.questionAnswers.offer(
            Pair(false, member)
        )
    }
}
