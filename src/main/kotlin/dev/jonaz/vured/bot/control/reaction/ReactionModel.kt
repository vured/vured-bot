package dev.jonaz.vured.bot.control.reaction

import dev.jonaz.vured.bot.control.ControlMessageCase

data class ReactionModel(
    val order: Int,
    val emote: String,
    val messageCase: ControlMessageCase,
    val clazz: Class<*>
)
