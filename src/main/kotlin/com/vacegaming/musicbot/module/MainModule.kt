package com.vacegaming.musicbot.module

import com.vacegaming.musicbot.service.MusicChannelService
import com.vacegaming.musicbot.service.ReactionService
import com.vacegaming.musicbot.service.StaticMessageService
import org.koin.dsl.module

val mainModule = module {
    single { StaticMessageService() }
    single { MusicChannelService() }

    factory { ReactionService(get()) }
}
