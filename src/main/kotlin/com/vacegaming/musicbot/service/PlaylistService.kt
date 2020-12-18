package com.vacegaming.musicbot.service

import com.sedmelluq.discord.lavaplayer.track.AudioTrack
import com.vacegaming.musicbot.util.koin.genericInject
import kotlinx.coroutines.*
import kotlinx.coroutines.channels.Channel
import net.dv8tion.jda.api.EmbedBuilder
import net.dv8tion.jda.api.entities.Member
import net.dv8tion.jda.api.entities.Message
import java.awt.Color

class PlaylistService {
    private val staticMessageService by genericInject<StaticMessageService>()
    private val musicChannelService by genericInject<MusicChannelService>()
    private val musicService by genericInject<MusicService>()
    private val logService by genericInject<LogService>()

    private var questionMessage: Message? = null
    private var questionJob: Deferred<Unit>? = null

    var questionAnswers: Channel<Pair<Boolean, Member>> = Channel()

    suspend fun askToAdd(member: Member, tracks: List<AudioTrack>) {
        val channel = musicChannelService.getTextChannel()
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
        val channel = musicChannelService.getTextChannel()

        questionAnswers = Channel()
        questionMessage?.let { message ->
            channel?.retrieveMessageById(message.idLong)?.queue { it.delete().queue() }
        }
        questionMessage = null

        if (questionJob?.isActive == true) questionJob?.cancel()
    }

    private fun addToQueue(tracks: List<AudioTrack>) {
        val audioPlayer = musicService.getAudioPlayer()
        deleteQuestionMessage()

        tracks.forEach(musicService::offerToQueue)

        staticMessageService.build(
            title = audioPlayer.playingTrack.info.title ?: "untitled",
            description = null,
            color = Color.GREEN,
            volume = audioPlayer.volume,
            queue = null
        ).also { staticMessageService.set(it) }
    }

    private suspend fun listenForAnswer(member: Member, tracks: List<AudioTrack>) {
        while (questionMessage != null) {
            val answer = questionAnswers.receive()

            if (answer.second == member) when (answer.first) {
                true -> {
                    logService.sendLog("Playlist importiert", "${tracks.size} tracks", member)
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
        //message.addReaction(ConfirmReaction.emote).queue()
        //message.addReaction(CancelReaction.emote).queue()
    }
}
