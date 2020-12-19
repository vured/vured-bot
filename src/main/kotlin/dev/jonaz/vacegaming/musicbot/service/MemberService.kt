package dev.jonaz.vacegaming.musicbot.service

import dev.jonaz.vacegaming.musicbot.util.data.Config
import dev.jonaz.vacegaming.musicbot.util.koin.genericInject
import net.dv8tion.jda.api.entities.Member

class MemberService {
    private val guildService by genericInject<GuildService>()

    fun isPermitted(member: Member?): Boolean {
        val memberId = Config.data.memberID
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
