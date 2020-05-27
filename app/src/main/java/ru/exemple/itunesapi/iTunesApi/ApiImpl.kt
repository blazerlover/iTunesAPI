package ru.exemple.itunesapi.iTunesApi

import ru.exemple.itunesapi.iTunesApi.model.Album
import ru.exemple.itunesapi.iTunesApi.model.DetailAlbum
import ru.exemple.itunesapi.iTunesApi.repository.HttpRequestExecutor
import ru.exemple.itunesapi.iTunesApi.util.JsonParser

class ApiImpl(
    private val jsonParser: JsonParser,
    private val httpRequestExecutor: HttpRequestExecutor
) : Api {

    override fun loadAlbums(artist: String): ArrayList<Album> {
        val response = httpRequestExecutor.makeAlbumsRequest(artist)
        return jsonParser.parseAlbumsResponse(response)
    }

    override fun loadDetailAlbum(albumId: Int): DetailAlbum {
        val response = httpRequestExecutor.makeDetailAlbumRequest(albumId)
        return jsonParser.parseDetailAlbumResponse(response)
    }
}