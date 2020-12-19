package com.vacegaming.musicbot

import com.vacegaming.musicbot.module.mainModule
import com.vacegaming.musicbot.service.MusicService
import com.vacegaming.musicbot.service.ReactionService
import com.vacegaming.musicbot.service.StaticMessageService
import com.vacegaming.musicbot.util.discord.DiscordClient
import com.vacegaming.musicbot.util.data.Config
import com.vacegaming.musicbot.util.environment.EnvironmentManager
import com.vacegaming.musicbot.util.koin.genericInject
import kotlinx.coroutines.runBlocking
import org.koin.core.context.startKoin

fun main(): Unit = runBlocking {
    startKoin {
        modules(mainModule)
    }

    EnvironmentManager.set()
    Config.load()
    DiscordClient.start()

    val musicService by genericInject<MusicService>()
    val reactionService by genericInject<ReactionService>()
    val staticMessageService by genericInject<StaticMessageService>()

    musicService.createAudioPlayer()
    reactionService.initReactions()
    staticMessageService.createBaseMessage()
}

