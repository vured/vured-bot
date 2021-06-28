package dev.jonaz.vured.bot.module

import dev.jonaz.vured.bot.service.web.JwtService
import dev.jonaz.vured.bot.service.web.PlayerService
import dev.jonaz.vured.bot.service.web.WebService
import org.koin.dsl.module

val webModule = module {
    single { WebService() }
    single { JwtService() }
    single { PlayerService() }
}
