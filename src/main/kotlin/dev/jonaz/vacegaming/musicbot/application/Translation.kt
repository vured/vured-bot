package dev.jonaz.vacegaming.musicbot.application

object Translation {
    /** User Messages **/
    const val NO_TRACK_TITLE = "Nothing is currently playing."
    const val NO_TRACK_DESCRIPTION =
        "Send a YouTube, SoundCloud, Bandcamp, Vimeo, Twitch Link or a direct URL to this channel to start music.\n" +
                "\n" +
                "You can also use the following commands to search:\n" +
                "\n" +
                "yscsearch: <search term>\n" +
                "scsearch: <search term>"

    const val UNKNOWN_TITLE = "Unknown title"
    const val UNKNOWN_DESCRIPTION = "Unknown description"

    const val VOLUME = "Volume"
    const val QUEUE = "Queue"

    const val NO_VOICECHANNEL = "Please go to a voice channel first."

    const val PLAYLIST_IMPORT_QUESTION = "Add a playlist?"
    const val PLAYLIST_IMPORT_DESCRIPTION =
        "Currently only one song from the playlist is played. Would you like to add the entire playlist (%v songs) to the queue?"

    const val NO_MATCHES = "No song was found."
    const val LOAD_FAILED = "Error at loading."
    const val TRACK_ADDED = "Song added."


    /** Logging Messages **/
    const val LOG_DATE = "Time: %v"

    const val LOG_APPLICATION_STARTED_TITLE = "Started"
    const val LOG_APPLICATION_STARTED_PRODUCTION = "The music bot was started on version %v."
    const val LOG_APPLICATION_STARTED_DEVELOPMENT = "The music bot was started in development mode."

    const val LOG_PLAYLIST_IMPORTED_TITLE = "Imported playlist"
    const val LOG_PLAYLIST_IMPORTED_DESCRIPTION = "%v imported a playlist."

    const val LOG_PAUSED_TITLE = "Song paused"
    const val LOG_PAUSED_DESCRIPTION = "The current song has been paused by %v."

    const val LOG_RESUMED_TITLE = "Song continued"
    const val LOG_RESUMED_DESCRIPTION = "The current song was continued by %v."

    const val LOG_SKIPPED_TITLE = "Skipped song"
    const val LOG_SKIPPED_DESCRIPTION = "%v skipped the current song."

    const val LOG_STOPPED_TITLE = "Song stopped"
    const val LOG_STOPPED_DESCRIPTION = "The audio player was stopped by %v."

    const val LOG_TRACK_LOADED_TITLE = "Song loaded"
    const val LOG_TRACK_LOADED_DESCRIPTION = "%v loaded a song."

    const val LOG_VOICECHANNEL_LEFT = "Left automatically"

    const val LOG_VOICECHANNEL_EMPTY_DESCRIPTION = "The bot left the voice channel as no one was inside."
    const val LOG_PLAYLIST_ENDED = "The bot left the voice channel as the playlist ended."

    const val LOG_TRACK_EXCEPTION = "An error occurred while playing the current song."
    const val LOG_TRACK_STUCK = "The song has frozen."
}
