package ru.exemple.itunesapi.iTunesApi

import ru.exemple.itunesapi.iTunesApi.model.Album
import ru.exemple.itunesapi.iTunesApi.model.DetailAlbum

interface Api {
    fun loadAlbums(artist: String): ArrayList<Album>
    fun loadDetailAlbum(albumId: Int): DetailAlbum
}