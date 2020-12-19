package com.vacegaming.musicbot.service

import com.vacegaming.musicbot.util.data.Config
import com.vacegaming.musicbot.util.koin.genericInject
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
        val channelId = member?.voiceState?.channel?.idLong
        val selfMember = guildService.getSelfMember()

        return selfMember?.voiceState?.channel?.idLong == channelId
    }
}
