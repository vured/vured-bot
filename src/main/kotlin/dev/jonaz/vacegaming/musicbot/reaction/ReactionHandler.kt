package dev.jonaz.vacegaming.musicbot.reaction

import net.dv8tion.jda.api.entities.Member

interface ReactionHandler {
    fun execute(member: Member)
}
