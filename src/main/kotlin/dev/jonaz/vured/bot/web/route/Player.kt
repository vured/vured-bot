package dev.jonaz.vured.bot.web.route

import dev.jonaz.vured.bot.service.music.MusicService
import dev.jonaz.vured.util.extensions.genericInject
import io.ktor.application.*
import io.ktor.http.*
import io.ktor.request.*
import io.ktor.response.*
import io.ktor.routing.*
import kotlinx.serialization.Serializable

fun Route.player() {
    val musicService by genericInject<MusicService>()

    patch("/player/volume") {
       call.receive<PlayerVolumeChangeDto>()
           .runCatching { musicService.setVolume(newVolume)  }
           .onFailure { call.respond(HttpStatusCode.BadRequest) }
           .onSuccess { call.respond(HttpStatusCode.OK) }
    }
}

@Serializable
data class PlayerVolumeChangeDto(
    val newVolume: Int
)
