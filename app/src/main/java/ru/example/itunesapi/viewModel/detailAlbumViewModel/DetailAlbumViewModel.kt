package ru.example.itunesapi.viewModel.detailAlbumViewModel

import androidx.databinding.ObservableField
import androidx.lifecycle.MutableLiveData
import ru.example.itunesapi.viewModel.row.DetailAlbumRow

interface DetailAlbumViewModel {
    fun getDetailAlbum(albumId: Int)
    fun getDetailAlbumRowLiveData(): MutableLiveData<DetailAlbumRow>
    fun getLoadingLiveData(): ObservableField<Boolean>
}