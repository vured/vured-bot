package com.vacegaming.musicbot.music

import com.sedmelluq.discord.lavaplayer.player.AudioLoadResultHandler
import com.sedmelluq.discord.lavaplayer.tools.FriendlyException
import com.sedmelluq.discord.lavaplayer.track.AudioPlaylist
import com.sedmelluq.discord.lavaplayer.track.AudioTrack
import com.vacegaming.musicbot.service.MusicChannelService
import com.vacegaming.musicbot.service.MusicService
import com.vacegaming.musicbot.service.PlaylistService
import com.vacegaming.musicbot.service.VoiceChannelService
import com.vacegaming.musicbot.util.ConfigManager
import com.vacegaming.musicbot.util.koin.genericInject
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import net.dv8tion.jda.api.entities.Member
import java.awt.Color

class AudioLoadResultManager(
    private val member: Member
) : AudioLoadResultHandler {
    private val voiceChannelService by genericInject<VoiceChannelService>()
    private val musicChannelService by genericInject<MusicChannelService>()
    private val playlistService by genericInject<PlaylistService>()
    private val musicService by genericInject<MusicService>()

    override fun trackLoaded(track: AudioTrack) {
        voiceChannelService
            .join(member)
            ?.apply {
                this.sendingHandler = musicService.getSendHandler()
            }?.run {
                TrackScheduler.queue(track)
            }
    }

    override fun playlistLoaded(playlist: AudioPlaylist) {
        val newTracks = playlist.tracks.take(ConfigManager.data.maxPlaylistTracks)

        voiceChannelService
            .join(member)
            ?.apply {
                this.sendingHandler = musicService.getSendHandler()
            }?.run {
                TrackScheduler.queue(playlist.selectedTrack ?: playlist.tracks[0])

                GlobalScope.launch {
                    playlistService.askToAdd(member, newTracks)
                }
            }
    }

    override fun noMatches() {
        musicChannelService.sendMessage(Color.RED, "Es wurde kein Song gefunden", 3000)
    }

    override fun loadFailed(exception: FriendlyException) {
        musicChannelService.sendMessage(Color.RED, "Fehler beim laden", 3000)
    }
}
