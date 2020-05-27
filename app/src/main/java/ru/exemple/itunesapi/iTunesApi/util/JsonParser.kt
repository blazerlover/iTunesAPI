package ru.exemple.itunesapi.iTunesApi.util

import org.json.JSONObject
import ru.exemple.itunesapi.*
import ru.exemple.itunesapi.iTunesApi.model.Album
import ru.exemple.itunesapi.iTunesApi.model.DetailAlbum
import ru.exemple.itunesapi.iTunesApi.model.Track

class JsonParser {

    fun parseAlbumsResponse(response: JSONObject): ArrayList<Album> {
        val albums = ArrayList<Album>()
        val jsonArray = response.getJSONArray(RESULT_PARSER_KEY)
        for (i in 0 until jsonArray.length()) {
            val artist = jsonArray.getJSONObject(i).getString(ARTIST_NAME_PARSER_KEY)
            val title = jsonArray.getJSONObject(i).getString(COLLECTION_NAME_PARSER_KEY)
            val albumPictureUrl = jsonArray.getJSONObject(i).getString(ARTWORK_URL_100_PARSER_KEY)
            val albumId = jsonArray.getJSONObject(i).getInt(COLLECTION_ID_PARSER_KEY)
            albums.add(Album(artist, title, albumPictureUrl, albumId))
        }
        return albums
    }

    fun parseDetailAlbumResponse(response: JSONObject): DetailAlbum {
        val tracks = ArrayList<Track>()
        val jsonArray = response.getJSONArray(RESULT_PARSER_KEY)
        val artist = jsonArray.getJSONObject(0).getString(ARTIST_NAME_PARSER_KEY)
        val album = jsonArray.getJSONObject(0).getString(COLLECTION_NAME_PARSER_KEY)
        val albumPictureUrl = jsonArray.getJSONObject(0).getString(ARTWORK_URL_100_PARSER_KEY)

        for (i in 1 until jsonArray.length()) {
            val trackName = jsonArray.getJSONObject(i).getString(TRACK_NAME_PARSER_KEY)
            val trackTime = jsonArray.getJSONObject(i).getInt(TRACK_TIME_MILLIS_PARSER_KEY)
            tracks.add(Track(i, trackName, trackTime))
        }
        return DetailAlbum(artist, album, albumPictureUrl, tracks)
    }
}