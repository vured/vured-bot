package dev.jonaz.vured.bot.control.button

import dev.jonaz.vured.bot.control.ControlMessageCase
import net.dv8tion.jda.api.interactions.components.ButtonStyle

data class ButtonModel(
    val order: Int,
    val identifier: String,
    val messageCase: ControlMessageCase,
    val style: ButtonStyle,
    val content: String,
    val clazz: Class<*>
)
