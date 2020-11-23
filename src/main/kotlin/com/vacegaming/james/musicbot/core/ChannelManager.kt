package com.vacegaming.james.musicbot.core

import com.vacegaming.james.musicbot.core.music.MusicQueue
import com.vacegaming.james.musicbot.core.reaction.*
import com.vacegaming.james.musicbot.util.ConfigManager
import com.vacegaming.james.musicbot.util.DiscordClient
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import net.dv8tion.jda.api.EmbedBuilder
import net.dv8tion.jda.api.entities.Member
import net.dv8tion.jda.api.entities.Message
import java.awt.Color

object ChannelManager {
    private val logChannelId = ConfigManager.data.botLogChannelID
    private val channelId = ConfigManager.data.musicBotChannelID
    private val jda = DiscordClient.client

    private lateinit var staticMessage: Message

    fun createStaticMessage() {
        val channel = jda.getTextChannelById(channelId) ?: return
        val eb = EmbedBuilder()

        eb.setTitle("Sende einen Song rein um ihn abzuspielen")
        eb.setColor(Color.RED)

        val message = eb.build()

        channel.sendMessage(message).queue {
            staticMessage = it
            createStaticReactions()
        }
    }

    fun editStaticMessage(title: String, color: Color, volume: Int?) {
        val eb = EmbedBuilder()
        val trackQueue = mutableListOf<String>()

        MusicQueue.queue.forEach { audioTrack ->
            trackQueue.add(audioTrack.info.title)
        }

        eb.setTitle(title)
        eb.setColor(color)

        volume?.let {
            eb.addField("Lautstärke", "$volume%", true)
        }

        if (trackQueue.size > 0) {
            eb.addField("Warteschlange", trackQueue.joinToString("\n"), false)
        }

        val message = eb.build()

        staticMessage.editMessage(message).queue()
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

    fun sendLog(title: String, text: String?, member: Member) {
        val channel = jda.getTextChannelById(logChannelId) ?: return
        val eb = EmbedBuilder()

        eb.setTitle(title)
        eb.setColor(Color.gray)
        eb.addField("Member:", member.asMention, true)

        text?.let { eb.setDescription(text) }

        val message = eb.build()

        channel.sendMessage(message).queue()
    }

    fun clearMessages() {
        val history = jda.getTextChannelById(channelId)?.iterableHistory ?: return

        history.takeAsync(50).thenApply {
            it.forEach { message ->
                if (message.idLong != staticMessage.idLong)
                    message.delete().queue()
            }
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
        staticMessage.addReaction(VolumeDownReaction.emote).queue()
        staticMessage.addReaction(VolumeUpReaction.emote).queue()
    }
}
