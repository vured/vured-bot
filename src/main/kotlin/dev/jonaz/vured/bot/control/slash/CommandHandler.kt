package dev.jonaz.vured.bot.control.slash

import net.dv8tion.jda.api.events.interaction.SlashCommandEvent
import net.dv8tion.jda.api.requests.restaction.CommandCreateAction

interface CommandHandler {
    fun upsertCommand(commandCreateAction: CommandCreateAction): CommandCreateAction
    fun execute(event: SlashCommandEvent)
}
