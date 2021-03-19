package dev.jonaz.vacegaming.musicbot.util.application

object Translation {
    /** User Messages **/
    const val NO_TRACK_TITLE = "Nothing is currently playing"
    const val NO_TRACK_DESCRIPTION =
        "Send a YouTube, SoundCloud, Bandcamp, Vimeo, Twitch Link or a direct URL to this channel to start music.\n" +
                "\n" +
                "You can also use the following commands to search:\n" +
                "\n" +
                "yscsearch: <search term>\n" +
                "scsearch: <search term>"

    const val UNKNOWN_TITLE = "Unknown title"
    const val UNKNOWN_DESCRIPTION = "Unknown description"

    const val VOLUME = "volume"
    const val QUEUE = "Queue"

    const val NO_VOICECHANNEL = "Please go to a voice channel first."

    const val PLAYLIST_IMPORT_QUESTION = "Add a playlist?"
    const val PLAYLIST_IMPORT_DESCRIPTION =
        "Currently only one song from the playlist is played.  Would you like to add the entire playlist (% v songs) to the queue?"

    const val NO_MATCHES = "No song was found."
    const val LOAD_FAILED = "Error at loading."
    const val TRACK_ADDED = "Song added."


    /** Logging Messages **/
    const val LOG_DATE = "Time:% v"

    const val LOG_APPLICATION_STARTED_TITLE = "Started"
    const val LOG_APPLICATION_STARTED_PRODUCTION = "The music bot was started on version% v."
    const val LOG_APPLICATION_STARTED_DEVELOPMENT = "Der Musikbot wurde im Entwicklungsmodus gestartet."

    const val LOG_PLAYLIST_IMPORTED_TITLE = "Playlist importiert"
    const val LOG_PLAYLIST_IMPORTED_DESCRIPTION = "%v hat eine Playlist Importiert."

    const val LOG_PAUSED_TITLE = "Song pausiert"
    const val LOG_PAUSED_DESCRIPTION = "Der aktuelle Song wurde von %v pausiert."

    const val LOG_RESUMED_TITLE = "Song fortgesetzt"
    const val LOG_RESUMED_DESCRIPTION = "Der aktuelle Song wurde von %v fortgesetzt."

    const val LOG_SKIPPED_TITLE = "Song übersprungen"
    const val LOG_SKIPPED_DESCRIPTION = "%v hat den aktuellen Song übersprungen."

    const val LOG_STOPPED_TITLE = "Gestoppt"
    const val LOG_STOPPED_DESCRIPTION = "Der Audioplayer wurde von %v gestoppt."

    const val LOG_TRACK_LOADED_TITLE = "Song geladen"
    const val LOG_TRACK_LOADED_DESCRIPTION = "%v hat einen Song geladen."

    const val LOG_VOICECHANNEL_LEFT = "Automatisch verlassen"

    const val LOG_VOICECHANNEL_EMPTY_DESCRIPTION = "Der Bot hat den Sprach-Kanal verlassen da er leer war."
    const val LOG_PLAYLIST_ENDED = "Der Bot hat den Sprach-Kanal verlassen da die Playlist vorbei war."

    const val LOG_TRACK_EXCEPTION = "Beim aktuellen Song ist ein Fehler aufgetreten."
    const val LOG_TRACK_STUCK = "Der Song hat sich aufgehangen."
}
