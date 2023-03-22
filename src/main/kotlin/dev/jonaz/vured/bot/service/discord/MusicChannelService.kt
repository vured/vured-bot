package dev.jonaz.vured.bot.service.discord

import dev.jonaz.vured.bot.service.application.ConfigService
import dev.jonaz.vured.bot.util.extensions.genericInject
import kotlinx.coroutines.*
import net.dv8tion.jda.api.EmbedBuilder
import net.dv8tion.jda.api.entities.Message
import net.dv8tion.jda.api.entities.channel.concrete.TextChannel
import java.awt.Color

class MusicChannelService {
    private val discordClientService by genericInject<DiscordClientService>()
    private val config by ConfigService

    fun getTextChannel(): TextChannel? {
        return discordClientService.jda.getTextChannelById(config.discord.musicChannel)
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

        channel.sendMessageEmbeds(message).queue {
            timeout?.run { deleteAfterTimeout(timeout, it) }
        }
    }

    private fun deleteAfterTimeout(timeout: Long, message: Message) {
        runBlocking(Dispatchers.IO) {
            delay(timeout)
            message.delete().queue()
        }
    }
}
