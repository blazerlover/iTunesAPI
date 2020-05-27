package ru.exemple.itunesapi.viewModel.albumsViewModel

import androidx.lifecycle.MutableLiveData
import ru.exemple.itunesapi.viewModel.row.AlbumRow

interface AlbumsViewModel {
    fun getAlbums(artist: String)
    fun getLiveData(): MutableLiveData<ArrayList<AlbumRow>>
}