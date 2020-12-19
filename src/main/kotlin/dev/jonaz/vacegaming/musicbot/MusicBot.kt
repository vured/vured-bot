package dev.jonaz.vacegaming.musicbot

import dev.jonaz.vacegaming.musicbot.module.mainModule
import dev.jonaz.vacegaming.musicbot.util.application.Application
import org.koin.core.context.startKoin

fun main() {
    startKoin {
        modules(mainModule)
    }.run(::Application)
}

