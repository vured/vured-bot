package dev.jonaz.vacegaming.musicbot.module

import dev.jonaz.vacegaming.musicbot.service.discord.*
import org.koin.dsl.module

val discordModule = module {
    single { DiscordClientService() }
    single { GuildService() }
    single { MemberService() }
    single { MusicChannelService() }
    single { ReactionService() }
    single { StaticMessageService() }
    single { VoiceChannelService() }
}
