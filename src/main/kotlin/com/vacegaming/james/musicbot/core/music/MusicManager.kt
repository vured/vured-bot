package com.vacegaming.james.musicbot.core.music

import com.sedmelluq.discord.lavaplayer.player.AudioLoadResultHandler
import com.sedmelluq.discord.lavaplayer.player.AudioPlayer
import com.sedmelluq.discord.lavaplayer.player.DefaultAudioPlayerManager
import com.sedmelluq.discord.lavaplayer.source.AudioSourceManagers
import com.sedmelluq.discord.lavaplayer.tools.FriendlyException
import com.sedmelluq.discord.lavaplayer.track.AudioPlaylist
import com.sedmelluq.discord.lavaplayer.track.AudioTrack
import com.vacegaming.james.musicbot.core.ChannelManager
import com.vacegaming.james.musicbot.core.VoiceChannelManager
import com.vacegaming.james.musicbot.util.ConfigManager
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import net.dv8tion.jda.api.entities.Member
import java.awt.Color
import java.util.concurrent.Future

object MusicManager {
    private val playerManager = DefaultAudioPlayerManager()
    private var sendHandler: AudioPlayerSendHandler

    val audioPlayer: AudioPlayer = playerManager.createPlayer()

    init {
        AudioSourceManagers.registerRemoteSources(playerManager)
        sendHandler = AudioPlayerSendHandler(audioPlayer)

        audioPlayer.addListener(TrackScheduler)
    }

    fun play(member: Member, url: String): Future<Void> = playerManager.loadItemOrdered(member.guild, url,
        object : AudioLoadResultHandler {
            override fun trackLoaded(track: AudioTrack) {
                if (member.guild.audioManager.isConnected.not()) {
                    VoiceChannelManager.join(member.voiceState?.channel)
                }

                member.guild.audioManager.sendingHandler = sendHandler
                TrackScheduler.queue(track)
            }

            override fun playlistLoaded(playlist: AudioPlaylist) {
                val newTracks = playlist.tracks.take(ConfigManager.data.maxPlaylistTracks)

                if (member.guild.audioManager.isConnected.not()) {
                    VoiceChannelManager.join(member.voiceState?.channel)
                }

                member.guild.audioManager.sendingHandler = sendHandler

                TrackScheduler.queue(playlist.selectedTrack ?: playlist.tracks.get(0))

                GlobalScope.launch {
                    PlaylistManager.askToAdd(member, newTracks)
                }
            }

            override fun noMatches() {
                ChannelManager.sendMessage(Color.RED, "Es wurde kein Song gefunden", 3000)
            }

            override fun loadFailed(exception: FriendlyException) {
                ChannelManager.sendMessage(Color.RED, "Fehler beim laden", 3000)
            }
        }
    )
}
