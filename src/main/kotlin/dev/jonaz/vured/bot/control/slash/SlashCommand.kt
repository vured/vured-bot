package dev.jonaz.vured.bot.control.slash

import org.atteo.classindex.IndexAnnotated

@IndexAnnotated
annotation class SlashCommand(
    val name: String,
    val description: String
)
