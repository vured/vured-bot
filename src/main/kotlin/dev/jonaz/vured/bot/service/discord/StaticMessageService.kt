package dev.jonaz.vured.bot.service.discord

import com.sedmelluq.discord.lavaplayer.track.AudioTrack
import dev.jonaz.vured.bot.application.Translation
import dev.jonaz.vured.bot.control.ControlMessageCase
import dev.jonaz.vured.bot.service.music.MusicService
import dev.jonaz.vured.bot.util.extensions.genericInject
import dev.jonaz.vured.bot.util.extensions.ifFalse
import net.dv8tion.jda.api.EmbedBuilder
import net.dv8tion.jda.api.entities.Message
import net.dv8tion.jda.api.entities.MessageEmbed
import java.awt.Color

class StaticMessageService {
    private val musicChannelService by genericInject<MusicChannelService>()
    private val reactionService by genericInject<ReactionService>()
    private val musicService by genericInject<MusicService>()
    private val buttonService by genericInject<ButtonService>()

    private lateinit var message: Message

    fun createBaseMessage() = build(
        title = Translation.NO_TRACK_TITLE,
        description = Translation.NO_TRACK_DESCRIPTION,
        color = Color.decode("#2F3136"),
        volume = null
    ).also {
        val channel = musicChannelService.getTextChannel()

        musicChannelService.clearMessages()
        channel?.sendMessageEmbeds(it)?.queue { message ->
            this.message = message
            setButtons()
            setReactions()
        }
    }

    fun build(
        title: String?,
        description: String?,
        color: Color?,
        volume: Int?,
        audioTrack: AudioTrack? = null
    ): MessageEmbed {
        val queue = musicService.getQueue()
        val trackQueue = mutableListOf<String>()

        queue.forEach {
            trackQueue.add(it.info.title ?: Translation.UNKNOWN_TITLE)
        }

        return EmbedBuilder().apply {
            title?.let {
                if (title.isBlank()) {
                    this.setAuthor(Translation.UNKNOWN_TITLE, audioTrack?.info?.uri)
                } else {
                    this.setAuthor(title, audioTrack?.info?.uri)
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

            audioTrack?.info?.let {
                this.setThumbnail("https://www.google.com/s2/favicons?sz=24&domain_url=${it.uri}")

                if (audioTrack.info.uri.startsWith("https://www.youtube.com/")) {
                    this.addField(Translation.CHANNEL, audioTrack.info.author, true)
                    this.setImage("https://img.youtube.com/vi/${audioTrack.info.identifier}/mqdefault.jpg")
                }
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
        message.editMessageEmbeds(messageEmbed).complete()
    }.getOrNull()

    private fun setReactions() {
        reactionService.getReactions(ControlMessageCase.STATIC).run {
            this.forEach { reactionService.addReaction(message, it.emote) }
        }
    }

    private fun setButtons() {
        buttonService.getButtons(ControlMessageCase.STATIC).run {
            buttonService.addButtons(message, this)
        }
    }
}
