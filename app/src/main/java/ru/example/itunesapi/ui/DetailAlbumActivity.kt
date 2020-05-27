package ru.example.itunesapi.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import ru.example.itunesapi.ALBUM_ID_KEY
import ru.example.itunesapi.App
import ru.example.itunesapi.R
import ru.example.itunesapi.ui.detailAlbumView.DetailAlbumView
import ru.example.itunesapi.ui.detailAlbumView.DetailAlbumViewImpl
import ru.example.itunesapi.viewModel.dataBinders.DetailAlbumDataBinder
import ru.example.itunesapi.viewModel.detailAlbumViewModel.DetailAlbumViewModel
import ru.example.itunesapi.viewModel.detailAlbumViewModel.DetailAlbumViewModelImpl
import ru.example.itunesapi.viewModel.row.DetailAlbumRow

class DetailAlbumActivity : AppCompatActivity() {

    private lateinit var liveData: MutableLiveData<DetailAlbumRow>
    private lateinit var detailAlbumView: DetailAlbumViewImpl
    private lateinit var detailAlbumViewModel: DetailAlbumViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail_album)
        setHomeButtonNavigation()
        init()
    }

    private fun init() {
        val api = (application as App).getDependencyRoot().api
        val detailAlbumDataBinder = DetailAlbumDataBinder()
        val factory =
            DetailAlbumViewModelImpl.DetailAlbumViewModelFactory(api, detailAlbumDataBinder)
        detailAlbumViewModel =
            ViewModelProvider(this, factory).get(DetailAlbumViewModelImpl::class.java)
        detailAlbumView =
            DetailAlbumViewImpl(
                findViewById(R.id.activity_detail_album__llDetailAlbum),
                detailAlbumViewModel,
                layoutInflater,
                intent.getIntExtra(ALBUM_ID_KEY, 0)
            )
        liveData = detailAlbumViewModel.getLiveData()
        liveData.observe(
            this,
            Observer { detailAlbumRow -> detailAlbumView.showData(detailAlbumRow) })
    }

    private fun setHomeButtonNavigation() {
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setHomeButtonEnabled(true)
    }
}