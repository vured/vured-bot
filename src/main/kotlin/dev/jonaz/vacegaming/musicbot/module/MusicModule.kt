package dev.jonaz.vacegaming.musicbot.module

import dev.jonaz.vacegaming.musicbot.service.music.MusicService
import dev.jonaz.vacegaming.musicbot.service.music.PlaylistService
import org.koin.dsl.module

val musicModule = module {
    single { MusicService() }
    single { PlaylistService() }
}
