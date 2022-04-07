FROM gradle:7.4.2-jdk17 AS build

COPY --chown=gradle:gradle . /home/gradle/src
WORKDIR /home/gradle/src
RUN gradle build --no-daemon

FROM openjdk:17-alpine

EXPOSE 8080

ENV BOT__TOKEN=""
ENV BOT__MAX_PLAYLIST_TRACKS=100
ENV DISCORD__GUILD=0
ENV DISCORD__LOG_CHANNEL=0
ENV DISCORD__MUSIC_CHANNEL=0
ENV DISCORD__ACCESS_ROLE=0

RUN mkdir /app

COPY --from=build /home/gradle/src/build/libs/ /app/

ENTRYPOINT ["java", "-Denv=prod", "-jar", "/app/vured-bot.jar"]
