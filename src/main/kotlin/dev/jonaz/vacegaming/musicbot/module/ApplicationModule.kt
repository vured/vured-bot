package dev.jonaz.vacegaming.musicbot.module

import dev.jonaz.vacegaming.musicbot.service.application.ConfigService
import dev.jonaz.vacegaming.musicbot.service.application.LogService
import dev.jonaz.vacegaming.musicbot.service.application.SentryService
import org.koin.dsl.module

val applicationModule = module {
    single { ConfigService() }
    single { LogService() }
    single { SentryService() }
}
