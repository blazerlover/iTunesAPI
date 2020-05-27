package ru.example.itunesapi.iTunesApi.model

class DetailAlbum(
    val artist: String,
    val title: String,
    val albumPictureUrl: String,
    val trackList: ArrayList<Track>
)