package dev.jonaz.vacegaming.musicbot.service.music

import com.sedmelluq.discord.lavaplayer.track.AudioTrack
import dev.jonaz.vacegaming.musicbot.reaction.ReactionMessageCase
import dev.jonaz.vacegaming.musicbot.service.application.LogService
import dev.jonaz.vacegaming.musicbot.service.discord.MusicChannelService
import dev.jonaz.vacegaming.musicbot.service.discord.ReactionService
import dev.jonaz.vacegaming.musicbot.service.discord.StaticMessageService
import dev.jonaz.vacegaming.musicbot.util.application.Translation
import dev.jonaz.vacegaming.musicbot.util.koin.genericInject
import kotlinx.coroutines.*
import kotlinx.coroutines.channels.Channel
import net.dv8tion.jda.api.EmbedBuilder
import net.dv8tion.jda.api.entities.Member
import net.dv8tion.jda.api.entities.Message
import java.awt.Color

class PlaylistService {
    private val staticMessageService by genericInject<StaticMessageService>()
    private val musicChannelService by genericInject<MusicChannelService>()
    private val reactionService by genericInject<ReactionService>()
    private val musicService by genericInject<MusicService>()
    private val logService by genericInject<LogService>()

    private var questionMessage: Message? = null
    private var questionJob: Deferred<Unit>? = null

    var questionAnswers: Channel<Pair<Boolean, Member>> = Channel()

    suspend fun askToAdd(member: Member, tracks: List<AudioTrack>) {
        val channel = musicChannelService.getTextChannel()
        val eb = EmbedBuilder()

        eb.setColor(Color.gray)
        eb.setTitle(Translation.PLAYLIST_IMPORT_QUESTION)
        eb.setDescription(Translation.PLAYLIST_IMPORT_DESCRIPTION.replace("%v,", tracks.size.toString()))

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
            title = audioPlayer.playingTrack.info.title ?: Translation.NO_TRACK_TITLE,
            description = null,
            color = Color.GREEN,
            volume = audioPlayer.volume
        ).also { staticMessageService.set(it) }
    }

    private suspend fun listenForAnswer(member: Member, tracks: List<AudioTrack>) {
        while (questionMessage != null) {
            val answer = questionAnswers.receive()

            if (answer.second == member) when (answer.first) {
                true -> {
                    logService.sendLog(
                        title = Translation.LOG_PLAYLIST_IMPORTED_TITLE,
                        description = Translation.LOG_PLAYLIST_IMPORTED_DESCRIPTION,
                        member = member,
                        color = Color.CYAN
                    )
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
        reactionService.getReactions(ReactionMessageCase.PLAYLIST).run {
            this.forEach { message.addReaction(it.emote).queue() }
        }
    }
}
