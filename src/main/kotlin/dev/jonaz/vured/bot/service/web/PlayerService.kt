package dev.jonaz.vured.bot.service.web

import com.sedmelluq.discord.lavaplayer.player.AudioPlayer
import dev.jonaz.vured.bot.persistence.web.PlayerEvent
import dev.jonaz.vured.bot.service.music.MusicService
import dev.jonaz.vured.util.extensions.genericInject
import io.ktor.http.cio.websocket.*
import kotlinx.coroutines.channels.BufferOverflow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.runBlocking
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json

class PlayerService {
    private val musicService by genericInject<MusicService>()
    val events = MutableSharedFlow<PlayerEvent>(
        extraBufferCapacity = 1,
        onBufferOverflow = BufferOverflow.DROP_OLDEST
    )

    fun sendEvent(audioPlayer: AudioPlayer) = runBlocking {
        mapEventFromAudioPlayer(audioPlayer).run {
            events.emit(this)
        }
    }

    fun getEvent(): PlayerEvent = musicService.getAudioPlayer().run {
        return mapEventFromAudioPlayer(this)
    }

    fun convertEventToFrame(event: PlayerEvent): Frame {
        val eventByteArray = Json.encodeToString(event).toByteArray()

        return Frame.byType(
            fin = true,
            frameType = FrameType.BINARY,
            data = eventByteArray
        )
    }

    private fun mapEventFromAudioPlayer(audioPlayer: AudioPlayer) = PlayerEvent(
        isPaused = audioPlayer.isPaused,
        volume = audioPlayer.volume,
        title = audioPlayer.playingTrack?.info?.title,
        author = audioPlayer.playingTrack?.info?.author,
        isStream = audioPlayer.playingTrack?.info?.isStream,
        uri = audioPlayer.playingTrack?.info?.uri,
        duration = audioPlayer.playingTrack?.duration,
        position = audioPlayer.playingTrack?.position
    )
}
