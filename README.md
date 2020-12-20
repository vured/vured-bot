# vaceGaming MusicBot ![CI](https://github.com/jonaznas/vacegaming-musicbot/workflows/CI/badge.svg)

<img align="right" src="https://i.imgur.com/RflqQ0I.png" width=25%>

This is a discord music bot based on [JDA](https://github.com/DV8FromTheWorld/JDA) and [Koin](https://github.com/InsertKoinIO/koin) developed for the vaceGaming community server.
vaceGaming was a registered esport organisation from germany and is now a private community server and invite only.

### 🛠 Development

You have to set the environment as a jvm argument. Choose between ``-Denv=dev`` and ``-Denv=prod``.

#### 🧱 Production:

```
./gradlew build
```

### Environment variables

Variable | Required | Description
-------- |  -------- | ------------
**BOT_TOKEN** | true | The discord bot token
**ROLE** | true | Role id that can access the bot
**GUILD** | true | Guild id
**LOG_CHANNEL** | true | Text-Channel id for logs
**MUSIC_CHANNEL** | true | Text-Channel id for the music bot
**SENTRY_DSN** | false | DSN for [Sentry](https://sentry.io)
