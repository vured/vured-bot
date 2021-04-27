# vaceGaming MusicBot ![CI](https://github.com/jonaznas/vacegaming-musicbot/workflows/CI/badge.svg)

<img align="right" src="https://i.imgur.com/RflqQ0I.png" width=25%>

[![Deploy](https://www.herokucdn.com/deploy/button.svg)](https://heroku.com/deploy?template=https://github.com/jonaznas/vacegaming-musicbot)

This is a discord music bot based on [JDA](https://github.com/DV8FromTheWorld/JDA) and [Koin](https://github.com/InsertKoinIO/koin) developed for the vaceGaming community server.
vaceGaming was a registered esport organisation from germany and is now a private community server and invite only.


![example-gif](https://i.imgur.com/pEhxoqc.gif)

You can edit the translations in [util/application/Translation.kt](https://github.com/jonaznas/vacegaming-musicbot/blob/master/src/main/kotlin/dev/jonaz/vacegaming/musicbot/util/application/Translation.kt)

### 🚢 Instant deploy with Docker

```console
docker run -d \
    --name vacegaming-musicbot \
    -e BOT__TOKEN="" \
    -e DISCORD__GUILD=0 \
    -e DISCORD__MUSIC_CHANNEL=0 \
    -e DISCORD__ACCESS_ROLE=0 \
    jonaznas/vacegaming-musicbot:latest
```


### 🛠 Development

You have to set the environment as a jvm argument. Choose between ``-Denv=dev`` and ``-Denv=prod``.

```
./gradlew build
```


### 📝 Config variables

Variable | Required | Description
-------- | ------------ | ------------
**BOT__TOKEN** | Yes | The discord bot token
**DISCORD__GUILD** | No* | Discord Guild ID
**DISCORD__LOG_CHANNEL** | No* | Text-Channel ID for logs
**DISCORD__MUSIC_CHANNEL** | No* | Text-Channel ID for static message
**DISCORD__ACCESS_ROLE** | No* | Role ID that can access the bot
**BOT__MAX_PLAYLIST_TRACKS** | No* | The maximum Tracks that can be in the queue
**SENTRY_DSN** | No | DSN for [Sentry](https://sentry.io)

*You can set all config variables in the [application.conf](https://github.com/jonaznas/vacegaming-musicbot/tree/master/src/main/resources) and optionally override it as an environment variable but **never put your Discord bot token into the config file**.
