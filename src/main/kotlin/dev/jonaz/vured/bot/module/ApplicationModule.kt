package dev.jonaz.vured.bot.module

import dev.jonaz.vured.bot.service.application.ConfigService
import dev.jonaz.vured.bot.service.application.LogService
import dev.jonaz.vured.bot.service.application.SentryService
import org.koin.dsl.module

val applicationModule = module {
    single { ConfigService() }
    single { LogService() }
    single { SentryService() }
}
