package dev.jonaz.vured.bot.service.discord

import dev.jonaz.vured.bot.service.application.ConfigService
import dev.jonaz.vured.util.extensions.genericInject
import net.dv8tion.jda.api.entities.Member

class MemberService {
    private val guildService by genericInject<GuildService>()
    private val config by ConfigService

    fun isPermitted(member: Member?): Boolean {
        val memberId = config.discord.accessRole
        val roleStream = member?.roles?.stream()

        val role = roleStream?.filter { it.idLong == memberId }?.findFirst()

        return role?.isEmpty?.not() ?: false
    }

    fun isInChannel(member: Member?): Boolean {
        val channelId = member?.voiceState?.channel?.idLong ?: return false
        val selfMember = guildService.getSelfMember() ?: return false

        return selfMember.voiceState?.channel?.idLong == channelId
    }
}
