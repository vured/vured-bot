package dev.jonaz.vacegaming.musicbot.util.application

import dev.jonaz.vacegaming.musicbot.util.environment.Environment
import dev.jonaz.vacegaming.musicbot.util.environment.EnvironmentType
import io.sentry.Sentry
import io.sentry.SentryOptions
import org.slf4j.LoggerFactory

object SentryClient {
    private val loggerFactory = LoggerFactory.getLogger(this::class.java)
    private val sentryDsn = System.getenv("SENTRY_DSN")

    fun init() = sentryDsn?.let {
        val options = getOptions()

        Sentry.init(options)
        loggerFactory.info("Sentry loaded in ${options.environment} environment")
    }

    private fun getOptions() = SentryOptions().apply {
        this.environment = getSentryEnvironment()
        this.beforeSend = getBeforeSendCallback()
        this.dsn = sentryDsn
    }

    private fun getBeforeSendCallback() = SentryOptions.BeforeSendCallback { event, hint ->
        loggerFactory.warn("Captured exception (${event.eventId})")
        event
    }

    private fun getSentryEnvironment() = when (Environment.type) {
        EnvironmentType.DEV -> "development"
        else -> "production"
    }
}
