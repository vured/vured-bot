package dev.jonaz.vacegaming.musicbot.util.discord

import dev.jonaz.vacegaming.musicbot.listener.GuildMessageReceivedLister
import dev.jonaz.vacegaming.musicbot.listener.GuildVoiceUpdateListener
import dev.jonaz.vacegaming.musicbot.listener.MessageReactionListener
import dev.jonaz.vacegaming.musicbot.util.data.Config
import net.dv8tion.jda.api.JDA
import net.dv8tion.jda.api.JDABuilder
import net.dv8tion.jda.api.requests.GatewayIntent
import net.dv8tion.jda.api.utils.MemberCachePolicy
import org.slf4j.LoggerFactory
import kotlin.system.exitProcess

object DiscordClient {
    private val logger = LoggerFactory.getLogger(this::class.java)

    lateinit var JDA: JDA

    fun start() = try {
        val intents = getRequiredIntents()

        JDA = JDABuilder
            .createDefault(Config.botToken, intents)
            .setMemberCachePolicy(MemberCachePolicy.ALL)
            .addEventListeners(
                GuildMessageReceivedLister(),
                GuildVoiceUpdateListener(),
                MessageReactionListener()
            )
            .build()
            .awaitReady()
    } catch (e: Exception) {
        logger.error(e.message)
        exitProcess(0)
    }

    private fun getRequiredIntents() = GatewayIntent.getIntents(
        GatewayIntent.ALL_INTENTS
    ).toMutableList()
}
