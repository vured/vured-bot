package com.vacegaming.musicbot.service

import com.vacegaming.musicbot.reaction.Reaction
import net.dv8tion.jda.api.entities.Emote
import net.dv8tion.jda.api.entities.Message
import org.atteo.classindex.ClassIndex

class ReactionService(
    private val message: Message
) {
    fun test() {
        getAllReactions()
    }

    fun create(emote: Emote) {
        //this.message.addReaction(emote).queue()
    }

    private fun getAllReactions() {
        val annotation = Reaction::class.java
        val classes = ClassIndex.getAnnotated(annotation)

        println(this.message)
        println(classes)
    }
}
