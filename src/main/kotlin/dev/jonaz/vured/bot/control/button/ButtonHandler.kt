package dev.jonaz.vured.bot.control.button

import net.dv8tion.jda.api.events.interaction.component.ButtonInteractionEvent

interface ButtonHandler {
    fun execute(event: ButtonInteractionEvent)
}
