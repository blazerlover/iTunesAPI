package ru.example.itunesapi.viewModel.albumsViewModel

import androidx.databinding.ObservableField
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import ru.example.itunesapi.iTunesApi.Api
import ru.example.itunesapi.viewModel.dataBinders.AlbumsDataBinder
import ru.example.itunesapi.viewModel.row.AlbumRow

class AlbumsViewModelImpl(val api: Api, val albumsDataBinder: AlbumsDataBinder) : AlbumsViewModel,
    ViewModel() {

    private var albumRows: ArrayList<AlbumRow>? = null
    private var liveData = MutableLiveData<ArrayList<AlbumRow>>()
    private val loadingLiveData = ObservableField<Boolean>()
    private val isUnsuccessfulTryLiveData = ObservableField<Boolean>()

    override fun getAlbums(artist: String) {
        loadingLiveData.set(true)
        object : Thread() {
            override fun run() {
                val albums = api.loadAlbums(artist)
                if (albums.size == 0) {
                    loadingLiveData.set(false)
                    isUnsuccessfulTryLiveData.set(true)
                    return
                }
                albumRows = albumsDataBinder.bindAlbumsRows(albums)
                liveData.postValue(albumRows)
                loadingLiveData.set(false)
                isUnsuccessfulTryLiveData.set(false)
            }
        }.start()
    }

    override fun getLiveData(): MutableLiveData<ArrayList<AlbumRow>> {
        return liveData
    }

    override fun getLoadingLiveData(): ObservableField<Boolean> {
        return loadingLiveData
    }

    override fun getIsUnsuccessfulTryLiveData(): ObservableField<Boolean> {
        return isUnsuccessfulTryLiveData
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