package dev.jonaz.vacegaming.musicbot.service

import dev.jonaz.vacegaming.musicbot.util.discord.DiscordClient
import dev.jonaz.vacegaming.musicbot.util.data.Config
import dev.jonaz.vacegaming.musicbot.util.data.Translation
import net.dv8tion.jda.api.EmbedBuilder
import net.dv8tion.jda.api.entities.Member
import net.dv8tion.jda.api.entities.TextChannel
import java.awt.Color
import java.text.SimpleDateFormat
import java.util.*

class LogService {
    fun sendLog(title: String, description: String?, member: Member?, color: Color?) {
        val channel = getTextChannel() ?: return
        val dateFormat = SimpleDateFormat("dd.MM.yyyy HH:mm:ss")
        val date = dateFormat.format(Date())

        val message = EmbedBuilder().apply {
            this.setTitle(title)

            color?.let {
                this.setColor(color)
            }

            description?.let {
                if (member == null) {
                    this.setDescription(description)
                } else {
                    this.setDescription(
                        description.replace("%v", "${member.asMention} (${member.user.asTag})")
                    )
                }
            }

            this.setFooter(Translation.LOG_DATE.replace("%v", date))
        }.run {
            this.build()
        }

        channel.sendMessage(message).queue()
    }

    private fun getTextChannel(): TextChannel? {
        return DiscordClient.JDA.getTextChannelById(Config.logChannel)
    }
}
