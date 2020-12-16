package com.vacegaming.musicbot.service

import net.dv8tion.jda.api.entities.Emote
import net.dv8tion.jda.api.entities.Message

class ReactionService(
    private val message: Message
) {
    fun create(emote: Emote) {
        this.message.addReaction(emote).queue()
    }
}
