package dev.jonaz.vured.bot.service.application

import dev.jonaz.vured.util.environment.Environment
import io.sentry.Sentry
import io.sentry.SentryOptions
import org.slf4j.LoggerFactory

class SentryService {
    private val loggerFactory = LoggerFactory.getLogger(this::class.java)
    private val sentryDsn = System.getenv("SENTRY_DSN")
    private val config by ConfigService

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

    private fun getSentryEnvironment() = when (config.env) {
        Environment.DEV -> "development"
        else -> "production"
    }
}
