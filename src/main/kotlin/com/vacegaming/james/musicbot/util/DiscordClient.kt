package com.vacegaming.james.musicbot.util

import org.slf4j.LoggerFactory
import net.dv8tion.jda.api.JDA
import net.dv8tion.jda.api.JDABuilder
import net.dv8tion.jda.api.requests.GatewayIntent
import net.dv8tion.jda.api.utils.MemberCachePolicy
import java.lang.Exception
import kotlin.system.exitProcess

object DiscordClient {
    private val logger = LoggerFactory.getLogger(this::class.java)

    lateinit var client: JDA

    fun start() = try {
        val token = ConfigManager.data.botToken
        val intents = getRequiredIntents()

        client = JDABuilder
            .createDefault(token, intents)
            .setMemberCachePolicy(MemberCachePolicy.ALL)
            .addEventListeners()
            .build()
    } catch (e: Exception) {
        logger.error(e.message)
        exitProcess(0)
    }

    private fun getRequiredIntents() = GatewayIntent.getIntents(
        GatewayIntent.ALL_INTENTS
    ).toMutableList()
}
