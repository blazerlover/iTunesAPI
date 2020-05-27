package ru.example.itunesapi.ui.detailAlbumView

import ru.example.itunesapi.ui.Loading
import ru.example.itunesapi.viewModel.row.DetailAlbumRow

interface DetailAlbumView: Loading {
    fun showData(detailAlbumRow: DetailAlbumRow)

    interface Listener {
        fun onDetailAlbumViewReady(albumId: Int)
    }
}