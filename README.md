<h1 align="center">Vured - A command-less music bot for Discord</h1>

<p align="center">
  <img src="https://i.imgur.com/oRkMRah.png" width="120px"/>
  <br>
  <i>
    Vured is a self-hosted music bot you can interact with by clicking buttons or using a web interface.<br>
    Say goodbye to commands and hello to enjoyable ui control.</i>
  <br>
</p>

<p align="center">
  <a href="https://github.com/vured/vured-bot/wiki">Wiki</a>
  ·
  <a href="https://vured-ui.jonaz.dev/">Web UI</a>
  <br>
  <br>
</p>

<p align="center">
    <a href="https://heroku.com/deploy?template=https://github.com/vured/vured-bot">
        <img src="https://www.herokucdn.com/deploy/button.svg" alt="heroku" />
    </a>
</p>

<hr>

Vured will be expanded in the future and new features will be added

#### Features
* [x] Static message
* [x] Discord buttons
* [x] Slash commands
* [x] Playlist import
* [x] YouTube search
* [x] Web UI

<br>

<img align="left" src="https://i.imgur.com/9p43DSl.png" width=39%>

<br>

<h3 align="center">A single text channel to control everything</h3>
<br>
<br>
<br>

<p align="center">
    A static message in your music text channel. Just send a link or search on YouTube.
    Change the volume or import an entire YouTube playlist with just one more click.
</p>

<br>

<img align="center" src="https://i.imgur.com/YVnwEOH.png">

<hr>

# A powerful web ui

<p>
    Some features cannot be comfortably used in a discord message.<br>
    <a href="https://github.com/vured/vured-bot/wiki/Access-web-ui">Learn more</a>
</p>

<img align="center" src="https://i.imgur.com/8uDavUf.png" >

<hr>

# It's self-hosted
Thanks to modern serverless platforms, however, it is easy and cheap or free of charge.<br>
Here are some services to deploy it.

- [Heroku](https://heroku.com/)
- [Fly](https://fly.io/)
- [Replit](https://replit.com/)
- [AppEngine](https://cloud.google.com/appengine)

### 🚢 Instant deploy with Docker

    docker run -d \
        --name vured-bot \
        -e BOT__TOKEN="" \
        -e DISCORD__GUILD=0 \
        -e DISCORD__MUSIC_CHANNEL=0 \
        -e DISCORD__ACCESS_ROLE=0 \
        jonaznas/vured-bot:latest

# Contributors

<a href = "https://github.com/vured/vured-bot/graphs/contributors">
  <img src = "https://contrib.rocks/image?repo=vured/vured-bot"/>
</a>
