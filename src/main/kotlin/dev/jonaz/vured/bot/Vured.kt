package dev.jonaz.vured.bot

import dev.jonaz.vured.bot.module.applicationModule
import dev.jonaz.vured.bot.module.discordModule
import dev.jonaz.vured.bot.module.musicModule
import dev.jonaz.vured.bot.application.Application
import dev.jonaz.vured.bot.module.webModule
import org.koin.core.context.startKoin

fun main() {
    startKoin {
        modules(applicationModule, discordModule, musicModule, webModule)
    }.run(::Application)
}

