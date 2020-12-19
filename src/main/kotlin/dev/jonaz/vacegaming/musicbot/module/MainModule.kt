package dev.jonaz.vacegaming.musicbot.module

import dev.jonaz.vacegaming.musicbot.service.*
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
