package com.vacegaming.musicbot.module

import com.vacegaming.musicbot.service.*
import net.dv8tion.jda.api.entities.Message
import org.koin.dsl.module

val mainModule = module {
    single { GuildService() }
    single { LogService() }
    single { MemberService() }
    single { MusicChannelService() }
    single { MusicService() }
    single { PlaylistService() }
    single { ReactionService() }
    single { StaticMessageService() }
    single { VoiceChannelService() }
}
