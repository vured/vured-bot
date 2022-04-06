package dev.jonaz.vured.bot.service.discord

import dev.jonaz.vured.bot.control.slash.CommandHandler
import dev.jonaz.vured.bot.control.slash.CommandModel
import dev.jonaz.vured.bot.control.slash.SlashCommand
import dev.jonaz.vured.bot.util.extensions.genericInject
import net.dv8tion.jda.api.events.interaction.SlashCommandEvent
import net.dv8tion.jda.api.requests.restaction.CommandCreateAction
import org.atteo.classindex.ClassIndex

class SlashCommandService {
    private val guildService by genericInject<GuildService>()

    private val commands = mutableListOf<CommandModel>()

    fun initCommands() {
        val annotation = SlashCommand::class.java
        val classes = ClassIndex.getAnnotated(annotation)
        val guild = guildService.getCurrentGuild() ?: return

        classes.forEach { clazz ->
            val annotationData = clazz.getAnnotation(annotation)
            val method = clazz.getMethod(CommandHandler::upsertCommand.name, CommandCreateAction::class.java)
            val instance = clazz.getDeclaredConstructor().newInstance()

            CommandModel(
                name = annotationData.name,
                description = annotationData.description,
                clazz = clazz
            ).also(commands::add)

            val commandCreateAction = guild.upsertCommand(annotationData.name, annotationData.description)
            val commandCreateActionResponse = method.invoke(instance, commandCreateAction) as CommandCreateAction

            commandCreateActionResponse.complete()
        }

        guild.updateCommands()
    }

    fun execute(event: SlashCommandEvent) {
        commands.findLast { it.name == event.name }?.run {
            val method = clazz.getMethod(CommandHandler::execute.name, SlashCommandEvent::class.java)
            val instance = clazz.getDeclaredConstructor().newInstance()

            method.invoke(instance, event)
        }
    }
}
