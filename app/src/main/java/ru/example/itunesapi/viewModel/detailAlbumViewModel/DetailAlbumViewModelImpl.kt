package ru.example.itunesapi.viewModel.detailAlbumViewModel

import androidx.databinding.ObservableField
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import ru.example.itunesapi.iTunesApi.Api
import ru.example.itunesapi.viewModel.dataBinders.DetailAlbumDataBinder
import ru.example.itunesapi.viewModel.row.DetailAlbumRow

class DetailAlbumViewModelImpl(
    private val api: Api,
    private val detailAlbumDataBinder: DetailAlbumDataBinder
) : DetailAlbumViewModel, ViewModel() {

    private var detailAlbumRow: DetailAlbumRow? = null

    private var detailAlbumRowLiveData = MutableLiveData<DetailAlbumRow>()
    private val loadingLiveData = ObservableField<Boolean>()

    init {
        loadingLiveData.set(true)
    }

    override fun getDetailAlbum(albumId: Int) {
        if (detailAlbumRowLiveData.value == null) {
            object : Thread() {
                override fun run() {
                    val detailAlbum = api.loadDetailAlbum(albumId)
                    detailAlbumRow = detailAlbumDataBinder.bindDetailAlbumRow(detailAlbum)
                    detailAlbumRowLiveData.postValue(detailAlbumRow)
                    loadingLiveData.set(false)
                }
            }.start()
        } else {
            detailAlbumRowLiveData.postValue(detailAlbumRow)
        }
    }

    override fun getDetailAlbumRowLiveData(): MutableLiveData<DetailAlbumRow> {
        return detailAlbumRowLiveData
    }

    override fun getLoadingLiveData(): ObservableField<Boolean> {
        return loadingLiveData
    }

    class DetailAlbumViewModelFactory(
        private val api: Api,
        private val detailAlbumDataBinder: DetailAlbumDataBinder
    ) :
        ViewModelProvider.Factory {

        override fun <T : ViewModel?> create(modelClass: Class<T>): T {
            return DetailAlbumViewModelImpl(api, detailAlbumDataBinder) as T
        }
    }
}