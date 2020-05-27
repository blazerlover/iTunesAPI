package ru.example.itunesapi.viewModel.row

class DetailAlbumRow(
    val artist: String,
    val title: String,
    val albumPictureUrl: String,
    val trackList: ArrayList<TrackRow>
)