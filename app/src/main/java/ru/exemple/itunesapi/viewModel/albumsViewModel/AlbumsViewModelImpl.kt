package ru.exemple.itunesapi.viewModel.albumsViewModel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import ru.exemple.itunesapi.iTunesApi.Api
import ru.exemple.itunesapi.iTunesApi.ApiImpl
import ru.exemple.itunesapi.viewModel.dataBinders.AlbumsDataBinder
import ru.exemple.itunesapi.viewModel.row.AlbumRow

class AlbumsViewModelImpl(val api: Api, val albumsDataBinder: AlbumsDataBinder) : AlbumsViewModel,
    ViewModel() {

    private var albumRows: ArrayList<AlbumRow>? = null

    private var liveData = MutableLiveData<ArrayList<AlbumRow>>()

    override fun getAlbums(artist: String) {
        if (liveData.value == null) {
            object : Thread() {
                override fun run() {
                    val albums = api.loadAlbums(artist)
                    albumRows = albumsDataBinder.bindAlbumsRows(albums)
                    liveData.postValue(albumRows)
                }
            }.start()
        } else {
            liveData.postValue(albumRows)
        }
    }

    override fun getLiveData(): MutableLiveData<ArrayList<AlbumRow>> {
        return liveData
    }

    class AlbumsViewModelFactory(
        private val api: Api,
        private val albumsDataBinder: AlbumsDataBinder
    ) :
        ViewModelProvider.Factory {

        override fun <T : ViewModel?> create(modelClass: Class<T>): T {
            return AlbumsViewModelImpl(api, albumsDataBinder) as T
        }
    }
}