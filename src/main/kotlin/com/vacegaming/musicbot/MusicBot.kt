package com.vacegaming.musicbot

import com.vacegaming.musicbot.module.mainModule
import com.vacegaming.musicbot.util.application.Application
import org.koin.core.context.startKoin

fun main() {
    startKoin {
        modules(mainModule)
    }.run(::Application)
}

