package com.vacegaming.james.musicbot

import com.sksamuel.hoplite.ConfigLoader
import com.vacegaming.james.musicbot.commands.Play
import net.dv8tion.jda.api.JDABuilder
import net.dv8tion.jda.api.requests.GatewayIntent
import net.dv8tion.jda.api.utils.MemberCachePolicy
import javax.security.auth.login.LoginException
import kotlin.system.exitProcess

lateinit var botConfig: BotConfig

fun main() {
    val jsonFile = when (System.getProperty("env")) {
        "dev" -> {
            "/default.json"
        }
        "production.json" -> {
            "/production.json.json"
        }
        else -> {
            println("Kein gültiges env angegeben!")
            println("Starte Bot im dev modus")
            "/default.json"
        }
    }

    botConfig = ConfigLoader().loadConfigOrThrow(jsonFile)

    val requiredIntents = GatewayIntent.getIntents(GatewayIntent.ALL_INTENTS).toMutableList()

    try {

        JDABuilder
            .createDefault(botConfig.botToken, requiredIntents)
            .setMemberCachePolicy(MemberCachePolicy.ALL)
            .addEventListeners(Play())
            .build()
    } catch (e: LoginException) {
        println(e.message)
        exitProcess(1)
    }

}
