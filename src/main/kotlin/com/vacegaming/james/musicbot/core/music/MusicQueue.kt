package com.vacegaming.james.musicbot.core.music

import kotlinx.coroutines.channels.Channel

object MusicQueue {
    private val queue = Channel<String>()

    fun add() {

    }

    private fun get() = queue
}
