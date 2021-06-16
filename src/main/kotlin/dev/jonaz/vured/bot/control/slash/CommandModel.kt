package dev.jonaz.vured.bot.control.slash

data class CommandModel(
    val name: String,
    val description: String,
    val clazz: Class<*>
)
