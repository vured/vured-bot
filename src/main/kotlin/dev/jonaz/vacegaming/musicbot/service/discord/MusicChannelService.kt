package dev.jonaz.vacegaming.musicbot.service.discord

import dev.jonaz.vacegaming.musicbot.service.application.ConfigService
import dev.jonaz.vured.util.extensions.genericInject
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import net.dv8tion.jda.api.EmbedBuilder
import net.dv8tion.jda.api.entities.Message
import net.dv8tion.jda.api.entities.TextChannel
import java.awt.Color

class MusicChannelService {
    private val discordClientService by genericInject<DiscordClientService>()
    private val config by ConfigService

    fun getTextChannel(): TextChannel? {
        return discordClientService.JDA.getTextChannelById(config.discord.musicChannel)
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
