package dev.jonaz.vured.bot.util.extensions

inline fun <T> Boolean?.ifTrue(supplier: () -> T) = if (this == true) supplier() else null

inline fun <T> Boolean?.ifFalse(supplier: () -> T) = if (this == false) supplier() else null

inline fun <T> Boolean?.ifNotTrue(supplier: () -> T) = if (this != true) supplier() else null

inline fun <T> Boolean?.ifNotFalse(supplier: () -> T) = if (this != false) supplier() else null
