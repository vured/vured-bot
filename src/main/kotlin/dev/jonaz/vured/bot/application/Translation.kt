package dev.jonaz.vured.bot.application

object Translation {
    /** User Messages **/
    const val NO_TRACK_TITLE = "Nothing is currently playing."
    const val NO_TRACK_DESCRIPTION = "Send a link in this channel to start."

    const val UNKNOWN_TITLE = "Unknown title"
    const val UNKNOWN_DESCRIPTION = "Unknown description"

    const val VOLUME = "Volume"
    const val QUEUE = "Queue"
    const val CHANNEL = "Channel"

    const val NO_PERMISSIONS = "You do not have permission to do this."
    const val NO_VOICE_CHANNEL = "Please go to a voice channel first."
    const val NOT_SAME_VOICE_CHANNEL = "You have to be in the same voice channel to to this."

    const val PLAYLIST_IMPORT_QUESTION = "Add a playlist?"
    const val PLAYLIST_IMPORT_DESCRIPTION =
        "Currently only one song from the playlist is played. Would you like to add the entire playlist (%v songs) to the queue?"
    const val PLAYLIST_IMPORT_BUTTON_CONFIRM = "Yep, import it"
    const val PLAYLIST_IMPORT_BUTTON_CANCEL = "Nah, cancel it"

    const val NO_MATCHES = "No song was found."
    const val LOAD_FAILED = "Error at loading."

    /** Command Descriptions **/
    const val COMMAND_VOLUME_DESC = "Individually set the volume"
    const val COMMAND_VOLUME_OPTION_NAME = "percent"
    const val COMMAND_VOLUME_OPTION_DESC = "New volume in %"
    const val COMMAND_VOLUME_RESPONSE = "Successfully changed the volume"

    const val COMMAND_YOUTUBE_DESC = "Search for a video name on youtube"
    const val COMMAND_YOUTUBE_OPTION_NAME = "query"
    const val COMMAND_YOUTUBE_OPTION_DESC = "Video name"
    const val COMMAND_YOUTUBE_RESPONSE = "Successfully searched on youtube"

    const val COMMAND_LOGIN_DESC = "Creates a token for the Web UI"
    const val COMMAND_LOGIN_RESPONSE =
        "Go to https://xxx.tld and enter the token. It's recommend to delete this message after using it."

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

    const val LOG_VOICE_CHANNEL_LEFT = "Left automatically"

    const val LOG_VOICE_CHANNEL_EMPTY_DESCRIPTION = "The bot left the voice channel as no one was inside."
    const val LOG_PLAYLIST_ENDED = "The bot left the voice channel as the playlist ended."

    const val LOG_TRACK_EXCEPTION = "An error occurred while playing the current song."
    const val LOG_TRACK_STUCK = "The song has frozen."
}
