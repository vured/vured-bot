package dev.jonaz.vacegaming.musicbot.application

import dev.jonaz.vacegaming.musicbot.service.application.ConfigService
import dev.jonaz.vacegaming.musicbot.service.application.LogService
import dev.jonaz.vacegaming.musicbot.service.application.SentryService
import dev.jonaz.vacegaming.musicbot.service.discord.DiscordClientService
import dev.jonaz.vacegaming.musicbot.service.music.MusicService
import dev.jonaz.vacegaming.musicbot.service.discord.ReactionService
import dev.jonaz.vacegaming.musicbot.service.discord.StaticMessageService
import dev.jonaz.vured.util.extensions.genericInject
import kotlinx.coroutines.runBlocking
import org.koin.core.KoinApplication
import org.koin.core.logger.Level

class Application(koinApplication: KoinApplication) {
    private val musicService by genericInject<MusicService>()
    private val reactionService by genericInject<ReactionService>()
    private val staticMessageService by genericInject<StaticMessageService>()
    private val logService by genericInject<LogService>()
    private val configService by genericInject<ConfigService>()
    private val sentryService by genericInject<SentryService>()
    private val discordClientService by genericInject<DiscordClientService>()

    companion object {
        lateinit var koin: KoinApplication
    }

    init {
        koinApplication.printLogger(level = Level.ERROR)
        koin = koinApplication
        start()
    }

    private fun start() = runBlocking {
        configService.loadConfig()
        discordClientService.start()
        sentryService.init()
        musicService.createAudioPlayer()
        reactionService.initReactions()
        staticMessageService.createBaseMessage()
        logService.sendStartupMessage()
    }
}
