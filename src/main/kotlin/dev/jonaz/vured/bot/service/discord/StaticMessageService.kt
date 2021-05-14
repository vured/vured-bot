package dev.jonaz.vured.bot.service.discord

import dev.jonaz.vured.bot.reaction.ReactionMessageCase
import dev.jonaz.vured.bot.service.music.MusicService
import dev.jonaz.vured.bot.application.Translation
import dev.jonaz.vured.util.extensions.genericInject
import dev.jonaz.vured.util.extensions.ifFalse
import net.dv8tion.jda.api.EmbedBuilder
import net.dv8tion.jda.api.entities.Message
import net.dv8tion.jda.api.entities.MessageEmbed
import java.awt.Color

class StaticMessageService {
    private val musicChannelService by genericInject<MusicChannelService>()
    private val reactionService by genericInject<ReactionService>()
    private val musicService by genericInject<MusicService>()

    private lateinit var message: Message

    fun createBaseMessage() = build(
        title = Translation.NO_TRACK_TITLE,
        description = Translation.NO_TRACK_DESCRIPTION,
        color = Color.RED,
        volume = null
    ).also {
        val channel = musicChannelService.getTextChannel()

        musicChannelService.clearMessages()
        channel?.sendMessage(it)?.queue { message ->
            this.message = message
            setReactions()
        }
    }

    fun build(
        title: String?,
        description: String?,
        color: Color?,
        volume: Int?
    ): MessageEmbed {
        val queue = musicService.getQueue()
        val trackQueue = mutableListOf<String>()

        queue.forEach { trackQueue.add(it.info.title ?: Translation.UNKNOWN_TITLE) }

        return EmbedBuilder().apply {
            title?.let {
                if (title.isBlank()) {
                    this.setTitle(Translation.UNKNOWN_TITLE)
                } else {
                    this.setTitle(title)
                }
            }

            description?.let {
                if (description.isBlank()) {
                    this.setDescription(Translation.UNKNOWN_DESCRIPTION)
                } else {
                    this.setDescription(description)
                }
            }

            color?.let {
                this.setColor(color)
            }

            volume?.let {
                this.addField(Translation.VOLUME, "$volume%", true)
            }

            queue.isEmpty().ifFalse {
                this.addField(
                    "${Translation.QUEUE} (${trackQueue.size})",
                    trackQueue.joinToString("\n").take(1024),
                    false
                )
            }
        }.run { return@run this.build() }
    }

    fun set(messageEmbed: MessageEmbed) = runCatching {
        message.editMessage(messageEmbed).complete()
    }.getOrNull()

    private fun setReactions() {
        reactionService.getReactions(ReactionMessageCase.STATIC).run {
            this.forEach { reactionService.addReaction(message, it.emote) }
        }
    }
}
