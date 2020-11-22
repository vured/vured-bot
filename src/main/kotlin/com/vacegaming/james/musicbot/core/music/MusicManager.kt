package com.vacegaming.james.musicbot.core.music

import com.sedmelluq.discord.lavaplayer.player.AudioLoadResultHandler
import com.sedmelluq.discord.lavaplayer.player.AudioPlayer
import com.sedmelluq.discord.lavaplayer.player.DefaultAudioPlayerManager
import com.sedmelluq.discord.lavaplayer.source.AudioSourceManagers
import com.sedmelluq.discord.lavaplayer.tools.FriendlyException
import com.sedmelluq.discord.lavaplayer.track.AudioPlaylist
import com.sedmelluq.discord.lavaplayer.track.AudioTrack
import net.dv8tion.jda.api.entities.Member
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
                member.guild.audioManager.sendingHandler = sendHandler
                TrackScheduler.queue(track)
            }

            override fun playlistLoaded(playlist: AudioPlaylist) {
                TrackScheduler.queue(playlist.selectedTrack ?: playlist.tracks.get(0))
            }

            override fun noMatches() {
                println("noMatches ($url)")
            }

            override fun loadFailed(exception: FriendlyException) {
                println("loadFailed")
            }
        }
    )
}
