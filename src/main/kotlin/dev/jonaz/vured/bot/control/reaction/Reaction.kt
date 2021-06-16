package dev.jonaz.vured.bot.control.reaction

import dev.jonaz.vured.bot.control.ControlMessageCase
import org.atteo.classindex.IndexAnnotated

@IndexAnnotated
annotation class Reaction(
    val order: Int,
    val emote: String,
    val messageCase: ControlMessageCase
)
