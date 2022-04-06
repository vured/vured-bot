package dev.jonaz.vured.bot.music

import com.sedmelluq.discord.lavaplayer.player.AudioLoadResultHandler
import com.sedmelluq.discord.lavaplayer.tools.FriendlyException
import com.sedmelluq.discord.lavaplayer.track.AudioPlaylist
import com.sedmelluq.discord.lavaplayer.track.AudioTrack
import dev.jonaz.vured.bot.application.Translation
import dev.jonaz.vured.bot.service.application.ConfigService
import dev.jonaz.vured.bot.service.application.LogService
import dev.jonaz.vured.bot.service.discord.MusicChannelService
import dev.jonaz.vured.bot.service.discord.VoiceChannelService
import dev.jonaz.vured.bot.service.music.MusicService
import dev.jonaz.vured.bot.service.music.PlaylistService
import dev.jonaz.vured.bot.service.web.PlayerService
import dev.jonaz.vured.bot.util.extensions.genericInject
import kotlinx.coroutines.runBlocking
import net.dv8tion.jda.api.entities.Member
import java.awt.Color

class AudioLoadResultManager(
    private val member: Member
) : AudioLoadResultHandler {
    private val voiceChannelService by genericInject<VoiceChannelService>()
    private val musicChannelService by genericInject<MusicChannelService>()
    private val playlistService by genericInject<PlaylistService>()
    private val playerService by genericInject<PlayerService>()
    private val musicService by genericInject<MusicService>()
    private val logService by genericInject<LogService>()
    private val config by ConfigService

    override fun trackLoaded(track: AudioTrack) {
        voiceChannelService
            .join(member)
            ?.apply {
                this.sendingHandler = musicService.getSendHandler()
            }?.run {
                musicService.queue(track)
            }
    }

    override fun playlistLoaded(playlist: AudioPlaylist) {
        val newTracks = playlist.tracks.take(config.bot.maxPlaylistTracks)

        voiceChannelService
            .join(member)
            ?.apply {
                this.sendingHandler = musicService.getSendHandler()
            }?.run {
                musicService.queue(playlist.selectedTrack ?: playlist.tracks[0])

                runBlocking {
                    playlistService.askToAdd(member, newTracks)
                }
            }
    }

    override fun noMatches() {
        musicChannelService.sendMessage(Color.RED, Translation.NO_MATCHES, 3000)
        playerService.sendMessageEvent(false, Translation.NO_MATCHES)
    }

    override fun loadFailed(exception: FriendlyException) {
        musicChannelService.sendMessage(Color.RED, exception.localizedMessage ?: Translation.LOAD_FAILED, 3000)

        runCatching {
            this.logService.sendLog(
                title = Translation.LOAD_FAILED,
                description = exception.localizedMessage,
                member = null,
                color = Color.RED
            )
            playerService.sendMessageEvent(true, exception.localizedMessage)
        }
    }
}
