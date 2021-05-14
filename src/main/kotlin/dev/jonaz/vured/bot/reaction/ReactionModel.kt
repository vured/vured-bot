package dev.jonaz.vured.bot.reaction

data class ReactionModel(
    val order: Int,
    val emote: String,
    val messageCase: ReactionMessageCase,
    val clazz: Class<*>
)
