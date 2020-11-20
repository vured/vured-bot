package com.vacegaming.james.musicbot.music

import com.vacegaming.james.musicbot.util.ConfigManager
import net.dv8tion.jda.api.entities.Member

object MusicUserPermission {

    fun isMemberPermitted(member: Member): Boolean {
        val clubMemberId = ConfigManager.data.clubMemberID
        val roleStream = member.roles.stream()

        val role = roleStream.filter { it.idLong == clubMemberId }.findFirst()

        return role.isEmpty.not()
    }
}
