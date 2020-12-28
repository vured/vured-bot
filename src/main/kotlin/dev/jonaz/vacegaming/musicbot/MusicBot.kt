package dev.jonaz.vacegaming.musicbot

import dev.jonaz.vacegaming.musicbot.module.applicationModule
import dev.jonaz.vacegaming.musicbot.module.discordModule
import dev.jonaz.vacegaming.musicbot.module.musicModule
import dev.jonaz.vacegaming.musicbot.util.application.Application
import org.koin.core.context.startKoin

fun main() {
    startKoin {
        modules(applicationModule, discordModule, musicModule)
    }.run(::Application)
}

