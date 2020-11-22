package com.vacegaming.james.musicbot.core

import com.vacegaming.james.musicbot.core.reaction.NextReaction
import com.vacegaming.james.musicbot.core.reaction.PauseReaction
import com.vacegaming.james.musicbot.core.reaction.PlayReaction
import com.vacegaming.james.musicbot.core.reaction.StopReaction
import com.vacegaming.james.musicbot.util.ConfigManager
import com.vacegaming.james.musicbot.util.DiscordClient
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import net.dv8tion.jda.api.EmbedBuilder
import net.dv8tion.jda.api.entities.Message
import net.dv8tion.jda.api.entities.VoiceChannel
import java.awt.Color

object ChannelManager {
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

    fun sendMessage(color: Color, text: String, timeout: Long? = null) {
        val channel = jda.getTextChannelById(channelId) ?: return
        val eb = EmbedBuilder()

        eb.setColor(color)
        eb.setDescription(text)

        val message = eb.build()

        channel.sendMessage(message).queue {
            timeout?.run { deleteAfterTimeout(timeout, it) }
        }
    }

    private fun deleteAfterTimeout(timeout: Long, message: Message) = GlobalScope.launch {
        delay(timeout)
        message.delete().queue()
    }

    private fun createStaticReactions() {
        staticMessage.addReaction(PlayReaction.emote).queue()
        staticMessage.addReaction(PauseReaction.emote).queue()
        staticMessage.addReaction(NextReaction.emote).queue()
        staticMessage.addReaction(StopReaction.emote).queue()
    }

    private fun clearMessages() {
        val history = jda.getTextChannelById(channelId)?.iterableHistory ?: return

        history.takeAsync(50).thenApply {
            it.forEach { message -> message.delete().queue() }
        }
    }
}
