package dev.jonaz.vured.bot.control.button

import dev.jonaz.vured.bot.control.ControlMessageCase
import net.dv8tion.jda.api.interactions.components.ButtonStyle
import org.atteo.classindex.IndexAnnotated

@IndexAnnotated
annotation class Button(
    val order: Int,
    val identifier: String,
    val style: ButtonStyle,
    val messageCase: ControlMessageCase,
    val content: String
)
