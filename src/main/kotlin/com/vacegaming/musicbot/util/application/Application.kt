package com.vacegaming.musicbot.util.application

import com.vacegaming.musicbot.service.MusicService
import com.vacegaming.musicbot.service.ReactionService
import com.vacegaming.musicbot.service.StaticMessageService
import com.vacegaming.musicbot.util.data.Config
import com.vacegaming.musicbot.util.discord.DiscordClient
import com.vacegaming.musicbot.util.environment.EnvironmentManager
import com.vacegaming.musicbot.util.koin.genericInject
import kotlinx.coroutines.runBlocking
import org.koin.core.KoinApplication
import org.koin.core.logger.Level

class Application(koinApplication: KoinApplication) {
    private val musicService by genericInject<MusicService>()
    private val reactionService by genericInject<ReactionService>()
    private val staticMessageService by genericInject<StaticMessageService>()

    companion object {
        lateinit var koin: KoinApplication
    }

    init {
        koinApplication.printLogger(level = Level.ERROR)
        koin = koinApplication
        start()
    }

    private fun start() = runBlocking {
        EnvironmentManager.set()
        Config.load()
        DiscordClient.start()

        musicService.createAudioPlayer()
        reactionService.initReactions()
        staticMessageService.createBaseMessage()
    }
}
