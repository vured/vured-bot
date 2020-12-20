package dev.jonaz.vacegaming.musicbot.util.application

import dev.jonaz.vacegaming.musicbot.service.LogService
import dev.jonaz.vacegaming.musicbot.service.MusicService
import dev.jonaz.vacegaming.musicbot.service.ReactionService
import dev.jonaz.vacegaming.musicbot.service.StaticMessageService
import dev.jonaz.vacegaming.musicbot.util.data.Config
import dev.jonaz.vacegaming.musicbot.util.data.Translation
import dev.jonaz.vacegaming.musicbot.util.discord.DiscordClient
import dev.jonaz.vacegaming.musicbot.util.environment.Environment
import dev.jonaz.vacegaming.musicbot.util.koin.genericInject
import io.sentry.Sentry
import kotlinx.coroutines.runBlocking
import org.koin.core.KoinApplication
import org.koin.core.logger.Level
import java.awt.Color

class Application(koinApplication: KoinApplication) {
    private val musicService by genericInject<MusicService>()
    private val reactionService by genericInject<ReactionService>()
    private val staticMessageService by genericInject<StaticMessageService>()
    private val logService by genericInject<LogService>()

    companion object {
        lateinit var koin: KoinApplication
    }

    init {
        koinApplication.printLogger(level = Level.ERROR)
        koin = koinApplication
        start()
    }

    private fun start() = runBlocking {
        val environmentDescription = Environment.set()

        Config.load()
        DiscordClient.start()
        SentryClient.init()

        musicService.createAudioPlayer()
        reactionService.initReactions()
        staticMessageService.createBaseMessage()

        logService.sendLog(
            title = Translation.LOG_APPLICATION_STARTED_TITLE,
            description = environmentDescription,
            member = null,
            color = Color(209, 236, 241)
        )
    }
}
