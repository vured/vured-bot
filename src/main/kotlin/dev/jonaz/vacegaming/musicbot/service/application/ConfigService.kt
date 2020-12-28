package dev.jonaz.vacegaming.musicbot.service.application

import com.sksamuel.hoplite.ConfigLoader
import com.sksamuel.hoplite.ConfigResult
import com.sksamuel.hoplite.EnvironmentVariablesPropertySource
import com.sksamuel.hoplite.PropertySource
import dev.jonaz.vacegaming.musicbot.model.config.ConfigModel
import dev.jonaz.vacegaming.musicbot.util.environment.Environment
import dev.jonaz.vacegaming.musicbot.util.environment.EnvironmentDecoder
import kotlin.reflect.KProperty

class ConfigService {
    companion object {
        private lateinit var data: ConfigModel

        operator fun getValue(thisRef: Any?, property: KProperty<*>): ConfigModel {
            return data
        }
    }

    fun loadConfig() {
        val environment = getEnvironment()
        val evpSource = getEnvironmentPropertySource()
        val configResult = buildConfig(environment, evpSource)

        configResult.map { data = it }

        if (configResult.isInvalid()) {
            throw Exception("Invalid configuration")
        }
    }

    private fun buildConfig(
        environment: Environment,
        evpSource: EnvironmentVariablesPropertySource
    ): ConfigResult<ConfigModel> {
        return ConfigLoader.Builder()
            .addPropertySource(evpSource)
            .addDecoder(EnvironmentDecoder())
            .addSource(PropertySource.resource(environment.config))
            .build()
            .loadConfig()
    }

    private fun getEnvironmentPropertySource(): EnvironmentVariablesPropertySource {
        return EnvironmentVariablesPropertySource(
            useUnderscoresAsSeparator = true,
            allowUppercaseNames = true
        )
    }

    private fun getEnvironment() = runCatching {
        enumValueOf<Environment>(System.getProperty("env").toUpperCase())
    }.getOrThrow()
}
