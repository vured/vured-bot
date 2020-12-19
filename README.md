# vaceGaming MusicBot

<a href="https://discord4j.com"><img align="right" src="https://i.imgur.com/RflqQ0I.png" width=27%></a>

![CI](https://github.com/jonaznas/vacegaming-musicbot/workflows/CI/badge.svg)

This is a discord music bot based on [JDA](https://github.com/DV8FromTheWorld/JDA) and [Koin](https://github.com/InsertKoinIO/koin) developed for the vaceGaming community server.
vaceGaming was a registered esport organisation from germany and is now a private community server and invite only.

### 🛠 Development

You have to set the environment as a jvm argument. Choose between ``-Denv=dev`` and ``-Denv=prod``.

#### 🧱 Production:

```
./gradlew build
```

### Environment variables

Variable | Environment | Description
-------- | ----------- | -----------
**BOT_TOKEN** | All | The discord bot token
