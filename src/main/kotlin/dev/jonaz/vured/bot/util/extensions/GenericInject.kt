package dev.jonaz.vured.bot.util.extensions

import org.koin.core.parameter.parametersOf
import org.koin.java.KoinJavaComponent

inline fun <reified T : Any> genericInject(vararg parameters: Any?): Lazy<T> {
    return KoinJavaComponent.inject(T::class.java) { parametersOf(*parameters) }
}
