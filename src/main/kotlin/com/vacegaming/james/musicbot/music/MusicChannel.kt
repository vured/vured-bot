package com.vacegaming.james.musicbot.music

import com.vacegaming.james.musicbot.util.ConfigManager
import com.vacegaming.james.musicbot.util.DiscordClient
import net.dv8tion.jda.api.EmbedBuilder
import net.dv8tion.jda.api.entities.Message
import java.awt.Color

object MusicChannel {
    private val channelId = ConfigManager.data.musicBotChannelID
    private val jda = DiscordClient.client

    private lateinit var staticMessage: Message

    init {
        clearMessages()
    }

    fun createStaticMessage() {
        val channel = jda.getTextChannelById(channelId) ?: return
        val eb = EmbedBuilder()

        eb.setTitle("test title")
        eb.setColor(Color.green)
        eb.setDescription("test description")
        eb.addField("test field", "testfield", false)

        val message = eb.build()

        channel.sendMessage(message).queue {
            staticMessage = it
            createStaticReactions()
        }
    }

    private fun createStaticReactions() {
        staticMessage.addReaction("U+25B6").queue()
    }

    private fun clearMessages() {
        val history = jda.getTextChannelById(channelId)?.iterableHistory ?: return

        history.takeAsync(50).thenApply {
            it.forEach { message -> message.delete().queue() }
        }
    }
}
