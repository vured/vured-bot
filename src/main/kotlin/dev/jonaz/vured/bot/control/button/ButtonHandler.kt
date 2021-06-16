package dev.jonaz.vured.bot.control.button

import net.dv8tion.jda.api.events.interaction.ButtonClickEvent

interface ButtonHandler {
    fun execute(event: ButtonClickEvent)
}
