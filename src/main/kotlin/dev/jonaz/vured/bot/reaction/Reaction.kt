package dev.jonaz.vured.bot.reaction

import org.atteo.classindex.IndexAnnotated

@IndexAnnotated
annotation class Reaction(
    val order: Int,
    val emote: String,
    val messageCase: ReactionMessageCase
)
