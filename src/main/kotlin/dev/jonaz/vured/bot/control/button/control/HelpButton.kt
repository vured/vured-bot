package dev.jonaz.vured.bot.control.button.control

import dev.jonaz.vured.bot.control.ControlMessageCase
import dev.jonaz.vured.bot.control.button.Button
import dev.jonaz.vured.bot.control.button.ButtonHandler
import net.dv8tion.jda.api.events.interaction.ButtonClickEvent
import net.dv8tion.jda.api.interactions.components.ButtonStyle

@Button(
    order = 5,
    identifier = "https://github.com/vured/vured-bot/wiki",
    style = ButtonStyle.LINK,
    messageCase = ControlMessageCase.STATIC,
    content = "Help"
)
class HelpButton : ButtonHandler {
    override fun execute(event: ButtonClickEvent) {
    }
}
