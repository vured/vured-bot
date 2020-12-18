package com.vacegaming.musicbot.module

import com.vacegaming.musicbot.service.*
import net.dv8tion.jda.api.entities.Message
import org.koin.dsl.module

val mainModule = module {
    single { LogService() }
    single { MusicChannelService() }
    single { MusicService() }
    single { PlaylistService() }
    single { StaticMessageService() }
    single { VoiceChannelService() }

    factory { (message: Message) ->  ReactionService(message) }
}
