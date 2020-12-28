package dev.jonaz.vacegaming.musicbot.util.application

object Translation {
    /** User Messages **/
    const val NO_TRACK_TITLE = "Derzeit wird nichts abgespielt"
    const val NO_TRACK_DESCRIPTION =
        "Sende einen YouTube, SoundCloud, Bandcamp, Vimeo, Twitch Link oder eine direkte URL in diesen Chat um zu starten.\n" +
                "\n" +
                "Du kannst auch folgende Befehle nutzen um zu suchen:\n" +
                "\n" +
                "ytsearch: <Suchbegriff>\n" +
                "scsearch: <Suchbegriff>"

    const val UNKNOWN_TITLE = "Unbekannter Titel"
    const val UNKNOWN_DESCRIPTION = "Unbekannte Beschreibung"

    const val VOLUME = "Lautstärke"
    const val QUEUE = "Warteschlange"

    const val NO_VOICECHANNEL = "Bitte gehe zuerst in einen Sprach-Kanal."

    const val PLAYLIST_IMPORT_QUESTION = "Playlist hinzufügen?"
    const val PLAYLIST_IMPORT_DESCRIPTION =
        "Derzeit wird nur ein einziger Song aus der Playlist abgespielt. Möchtest du die gesammte Playlist (%v songs) in die Warteschlange hinzufügen?"

    const val NO_MATCHES = "Es wurde kein Song gefunden."
    const val LOAD_FAILED = "Fehler beim laden."
    const val TRACK_ADDED = "Song hinzugefügt."


    /** Logging Messages **/
    const val LOG_DATE = "Zeit: %v"

    const val LOG_APPLICATION_STARTED_TITLE = "Gestartet"
    const val LOG_APPLICATION_STARTED_PRODUCTION = "Der Musikbot wurde auf der Version %v gestartet."
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
