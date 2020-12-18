package com.vacegaming.musicbot.service

import com.vacegaming.musicbot.util.ConfigManager
import com.vacegaming.musicbot.util.DiscordClient
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import net.dv8tion.jda.api.EmbedBuilder
import net.dv8tion.jda.api.entities.Message
import net.dv8tion.jda.api.entities.TextChannel
import java.awt.Color

class MusicChannelService {

    fun getTextChannel(): TextChannel? {
        return DiscordClient.jda.getTextChannelById(
            ConfigManager.data.musicBotChannelID
        )
    }

    fun clearMessages() {
        val history = getTextChannel()?.iterableHistory ?: return

        history.complete().take(50).forEach {
            it.delete().complete()
        }
    }

    fun sendMessage(color: Color, text: String, timeout: Long? = null) {
        val channel = getTextChannel() ?: return
        val message = EmbedBuilder().apply {
            this.setColor(color)
            this.setDescription(text)
        }.run { this.build() }

        channel.sendMessage(message).queue {
            timeout?.run { deleteAfterTimeout(timeout, it) }
        }
    }

    private fun deleteAfterTimeout(timeout: Long, message: Message) = GlobalScope.launch {
        delay(timeout)
        message.delete().queue()
    }
}
