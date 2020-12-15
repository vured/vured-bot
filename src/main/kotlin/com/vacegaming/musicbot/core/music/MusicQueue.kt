package com.vacegaming.musicbot.core.music

import com.sedmelluq.discord.lavaplayer.track.AudioTrack
import java.util.concurrent.BlockingQueue
import java.util.concurrent.LinkedBlockingQueue

object MusicQueue {
    var queue: BlockingQueue<AudioTrack> = LinkedBlockingQueue()
}
