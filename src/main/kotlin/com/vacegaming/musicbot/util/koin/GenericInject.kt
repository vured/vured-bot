package com.vacegaming.musicbot.util.koin

import org.koin.core.parameter.parametersOf
import org.koin.java.KoinJavaComponent.inject

inline fun <reified T : Any> genericInject(vararg parameters: Any?): Lazy<T> {
    return inject(T::class.java) { parametersOf(*parameters) }
}
