package ru.example.itunesapi.ui.albumsView

import ru.example.itunesapi.ui.Loading
import ru.example.itunesapi.viewModel.row.AlbumRow

interface AlbumView: Loading {
    fun showData(albums: ArrayList<AlbumRow>)

    interface OnAlbumsAdapterItemClickListener {
        fun onAlbumsAdapterItemClicked(albumId: Int)
    }

    interface Listener {
        fun onTextQueryReady(textQuery: String)
    }
}