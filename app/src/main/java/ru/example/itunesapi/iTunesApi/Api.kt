package ru.example.itunesapi.iTunesApi

import ru.example.itunesapi.iTunesApi.model.Album
import ru.example.itunesapi.iTunesApi.model.DetailAlbum

interface Api {
    fun loadAlbums(artist: String): ArrayList<Album>
    fun loadDetailAlbum(albumId: Int): DetailAlbum
}