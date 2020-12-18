package com.vacegaming.musicbot.service

import com.vacegaming.musicbot.util.ConfigManager
import com.vacegaming.musicbot.util.koin.genericInject
import net.dv8tion.jda.api.EmbedBuilder
import net.dv8tion.jda.api.entities.Message
import net.dv8tion.jda.api.entities.MessageEmbed
import java.awt.Color

class StaticMessageService {
    private val musicChannelService by genericInject<MusicChannelService>()

    private lateinit var message: Message

    fun createBaseMessage() = build(
        title = "Derzeit wird nichts abgespielt",
        description = ConfigManager.data.defaultMessage,
        color = Color.RED,
        volume = null,
        queue = null
    ).also {
        val channel = musicChannelService.getTextChannel()

        musicChannelService.clearMessages()
        channel?.sendMessage(it)?.queue { message ->
            this.message = message
            setReactions()
        }
    }

    private fun setReactions() {
        val reactionService by genericInject<ReactionService>(message)

        reactionService.test()
    }

    fun build(
        title: String?,
        description: String?,
        color: Color?,
        volume: Int?,
        queue: List<String>?
    ): MessageEmbed {
        return EmbedBuilder().apply {
            title?.let {
                this.setTitle("Derzeit wird nichts abgespielt")
            }

            description?.let {
                this.setDescription(ConfigManager.data.defaultMessage)
            }

            color?.let {
                this.setColor(Color.RED)
            }

            volume?.let {
                this.addField("Lautstärke", "$volume%", true)
            }

            queue?.let {
                this.addField("Warteschlange (${queue.size})", queue.joinToString("\n").take(1024), false)
            }
        }.run { return@run this.build() }
    }

    fun set(message: MessageEmbed) = this.message.editMessage(message)
}
