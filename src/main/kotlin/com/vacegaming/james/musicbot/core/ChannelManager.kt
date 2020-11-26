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
import java.text.SimpleDateFormat
import java.util.*

object ChannelManager {
    private val logChannelId = ConfigManager.data.botLogChannelID
    private val channelId = ConfigManager.data.musicBotChannelID
    private val jda = DiscordClient.jda

    private lateinit var staticMessage: Message

    fun createStaticMessage() {
        val channel = jda.getTextChannelById(channelId) ?: return
        val eb = EmbedBuilder()

        eb.setTitle("Derzeit wird nichts abgespielt")
        eb.setDescription(ConfigManager.data.defaultMessage)
        eb.setColor(Color.RED)

        val message = eb.build()

        channel.sendMessage(message).queue {
            staticMessage = it
            createStaticReactions()
        }
    }

    fun editStaticMessage(title: String, description: String?, color: Color, volume: Int?) {
        val eb = EmbedBuilder()
        val trackQueue = mutableListOf<String>()

        MusicQueue.queue.forEach { audioTrack ->
            trackQueue.add(audioTrack.info.title)
        }

        eb.setTitle(title)

        description?.let { eb.setDescription(it) }

        eb.setColor(color)

        volume?.let {
            eb.addField("Lautstärke", "$volume%", true)
        }

        if (trackQueue.size > 0) {
            eb.addField("Warteschlange (${trackQueue.size})", trackQueue.joinToString("\n").take(1024), false)
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

    fun sendLog(title: String, text: String? = null, member: Member? = null) {
        val dateFormat = SimpleDateFormat("dd.MM.yyyy HH:mm:ss")
        val channel = jda.getTextChannelById(logChannelId) ?: return
        val eb = EmbedBuilder()

        eb.setTitle(title)
        eb.setColor(Color.gray)

        member?.let { eb.addField("Member:", member.asMention, true) }

        eb.setFooter("Uhrzeit: ${dateFormat.format(Date())}", null)

        text?.let { eb.setDescription(text) }

        val message = eb.build()

        channel.sendMessage(message).queue()
    }

    fun clearMessages() {
        val history = jda.getTextChannelById(channelId)?.iterableHistory ?: return

        history.complete().take(50).forEach {
            it.delete().complete()
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
