package ru.example.itunesapi.viewModel.albumsViewModel

import androidx.lifecycle.MutableLiveData
import ru.example.itunesapi.ui.albumsView.AlbumView
import ru.example.itunesapi.viewModel.row.AlbumRow

interface AlbumsViewModel: AlbumView.Listener {
    fun getAlbums(artist: String)
    fun getLiveData(): MutableLiveData<ArrayList<AlbumRow>>
}