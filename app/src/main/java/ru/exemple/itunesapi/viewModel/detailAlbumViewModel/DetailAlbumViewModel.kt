package ru.exemple.itunesapi.viewModel.detailAlbumViewModel

import androidx.lifecycle.MutableLiveData
import ru.exemple.itunesapi.iTunesApi.model.DetailAlbum
import ru.exemple.itunesapi.viewModel.row.DetailAlbumRow

interface DetailAlbumViewModel {
    fun getDetailAlbum(albumId: Int)
    fun getLiveData(): MutableLiveData<DetailAlbumRow>
}