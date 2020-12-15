package com.vacegaming.musicbot.core.music

import com.sedmelluq.discord.lavaplayer.track.AudioTrack
import com.vacegaming.musicbot.core.ChannelManager
import com.vacegaming.musicbot.core.reaction.CancelReaction
import com.vacegaming.musicbot.core.reaction.ConfirmReaction
import com.vacegaming.musicbot.util.ConfigManager
import com.vacegaming.musicbot.util.DiscordClient
import kotlinx.coroutines.*
import kotlinx.coroutines.channels.Channel
import net.dv8tion.jda.api.EmbedBuilder
import net.dv8tion.jda.api.entities.Member
import net.dv8tion.jda.api.entities.Message
import java.awt.Color

object PlaylistManager {
    private val channelId = ConfigManager.data.musicBotChannelID
    private val jda = DiscordClient.jda

    private var questionMessage: Message? = null
    private var questionJob: Deferred<Unit>? = null

    var questionAnswers: Channel<Pair<Boolean, Member>> = Channel()

    suspend fun askToAdd(member: Member, tracks: List<AudioTrack>) {
        val channel = jda.getTextChannelById(channelId)
        val eb = EmbedBuilder()

        eb.setColor(Color.gray)
        eb.setTitle("Playlist hinzufügen?")
        eb.setDescription("Derzeit wird nur ein einziger Song aus der Playlist abgespielt. Möchtest du die gesammte Playlist (${tracks.size} songs) in die Warteschlange hinzufügen?")

        val message = eb.build()

        deleteQuestionMessage()
        channel?.sendMessage(message)?.complete()?.let {
            questionMessage = it
            createMessageReactions(it)
        }

        withContext(Dispatchers.IO) {
            questionJob = async {
                launch {
                    delay(10000L)
                    deleteQuestionMessage()
                }
                listenForAnswer(member, tracks)
            }
        }
    }

    fun deleteQuestionMessage() {
        val channel = jda.getTextChannelById(channelId)

        questionAnswers = Channel()
        questionMessage?.let { message ->
            channel?.retrieveMessageById(message.idLong)?.queue { it.delete().queue() }
        }
        questionMessage = null

        if (questionJob?.isActive == true) questionJob?.cancel()
    }


    private fun addToQueue(tracks: List<AudioTrack>) {
        val audioPlayer = MusicManager.audioPlayer
        deleteQuestionMessage()

        tracks.forEach {
            MusicQueue.queue.offer(it)
        }

        ChannelManager.editStaticMessage(audioPlayer.playingTrack.info.title, null, Color.green, audioPlayer.volume)
    }

    private suspend fun listenForAnswer(member: Member, tracks: List<AudioTrack>) {
        while (questionMessage != null) {
            val answer = questionAnswers.receive()

            if (answer.second == member) when (answer.first) {
                true -> {
                    ChannelManager.sendLog("Playlist importiert", "${tracks.size} tracks", member)
                    addToQueue(tracks.drop(1))
                    break
                }
                false -> {
                    deleteQuestionMessage()
                    break
                }
            }
        }
    }

    private fun createMessageReactions(message: Message) {
        message.addReaction(ConfirmReaction.emote).queue()
        message.addReaction(CancelReaction.emote).queue()
    }
}
