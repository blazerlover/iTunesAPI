package ru.example.itunesapi.viewModel.detailAlbumViewModel

import androidx.lifecycle.MutableLiveData
import ru.example.itunesapi.ui.detailAlbumView.DetailAlbumView
import ru.example.itunesapi.viewModel.row.DetailAlbumRow

interface DetailAlbumViewModel: DetailAlbumView.Listener {
    fun getDetailAlbum(albumId: Int)
    fun getLiveData(): MutableLiveData<DetailAlbumRow>
}