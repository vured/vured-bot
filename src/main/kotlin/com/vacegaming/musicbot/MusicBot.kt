package com.vacegaming.musicbot

import com.vacegaming.musicbot.module.mainModule
import com.vacegaming.musicbot.service.StaticMessageService
import com.vacegaming.musicbot.util.ConfigManager
import com.vacegaming.musicbot.util.DiscordClient
import com.vacegaming.musicbot.util.environment.EnvironmentManager
import com.vacegaming.musicbot.util.koin.genericInject
import kotlinx.coroutines.runBlocking
import org.koin.core.context.startKoin

fun main(): Unit = runBlocking {
    startKoin {
        modules(mainModule)
    }

    EnvironmentManager.set()
    ConfigManager.setConfigFile()
    DiscordClient.start()

    val staticMessageService by genericInject<StaticMessageService>()

    staticMessageService.createBaseMessage()
}

