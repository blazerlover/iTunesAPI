package ru.example.itunesapi.viewModel.albumsViewModel

import androidx.databinding.ObservableField
import androidx.lifecycle.MutableLiveData
import ru.example.itunesapi.viewModel.row.AlbumRow

interface AlbumsViewModel {
    fun getAlbums(artist: String)
    fun getLiveData(): MutableLiveData<ArrayList<AlbumRow>>
    fun getLoadingLiveData(): ObservableField<Boolean>
    fun getIsUnsuccessfulTryLiveData(): ObservableField<Boolean>
}