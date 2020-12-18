package com.vacegaming.musicbot.service

import com.vacegaming.musicbot.util.koin.genericInject
import net.dv8tion.jda.api.EmbedBuilder
import net.dv8tion.jda.api.entities.Member
import java.awt.Color
import java.text.SimpleDateFormat
import java.util.*

class LogService {
    private val musicChannelService by genericInject<MusicChannelService>()

    fun sendLog(title: String, text: String? = null, member: Member? = null) {
        val dateFormat = SimpleDateFormat("dd.MM.yyyy HH:mm:ss")
        val channel = musicChannelService.getTextChannel() ?: return
        val eb = EmbedBuilder()

        eb.setTitle(title)
        eb.setColor(Color.gray)

        member?.let { eb.addField("Member:", member.asMention, true) }

        eb.setFooter("Uhrzeit: ${dateFormat.format(Date())}", null)

        text?.let { eb.setDescription(text) }

        val message = eb.build()

        channel.sendMessage(message).queue()
    }
}
