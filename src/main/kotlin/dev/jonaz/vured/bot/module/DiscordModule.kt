package dev.jonaz.vured.bot.module

import dev.jonaz.vured.bot.service.discord.*
import org.koin.dsl.module

val discordModule = module {
    single { DiscordClientService() }
    single { GuildService() }
    single { MemberService() }
    single { MusicChannelService() }
    single { ReactionService() }
    single { StaticMessageService() }
    single { VoiceChannelService() }
    single { ButtonService() }
    single { SlashCommandService() }
}
