package ru.exemple.itunesapi.viewModel.detailAlbumViewModel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import ru.exemple.itunesapi.iTunesApi.Api
import ru.exemple.itunesapi.viewModel.dataBinders.DetailAlbumDataBinder
import ru.exemple.itunesapi.viewModel.row.DetailAlbumRow

class DetailAlbumViewModelImpl(
    private val api: Api,
    private val detailAlbumDataBinder: DetailAlbumDataBinder
) : DetailAlbumViewModel, ViewModel() {

    private var detailAlbumRow: DetailAlbumRow? = null

    private var liveData = MutableLiveData<DetailAlbumRow>()

    override fun getDetailAlbum(albumId: Int) {
        if (liveData.value == null) {
            object : Thread() {
                override fun run() {
                    val detailAlbum = api.loadDetailAlbum(albumId)
                    detailAlbumRow = detailAlbumDataBinder.bindDetailAlbumRow(detailAlbum)
                    liveData.postValue(detailAlbumRow)
                }
            }.start()
        } else {
            liveData.postValue(detailAlbumRow)
        }
    }

    override fun getLiveData(): MutableLiveData<DetailAlbumRow> {
        return liveData
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