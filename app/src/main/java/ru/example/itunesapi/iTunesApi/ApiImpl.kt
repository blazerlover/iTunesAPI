package ru.example.itunesapi.iTunesApi

import ru.example.itunesapi.iTunesApi.model.Album
import ru.example.itunesapi.iTunesApi.model.DetailAlbum
import ru.example.itunesapi.iTunesApi.repository.HttpRequestExecutor
import ru.example.itunesapi.iTunesApi.util.JsonParser

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