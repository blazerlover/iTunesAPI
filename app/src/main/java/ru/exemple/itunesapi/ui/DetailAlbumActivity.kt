package ru.exemple.itunesapi.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.ViewModelProvider
import ru.exemple.itunesapi.ALBUM_ID_KEY
import ru.exemple.itunesapi.App
import ru.exemple.itunesapi.R
import ru.exemple.itunesapi.ui.detailAlbumView.DetailAlbumView
import ru.exemple.itunesapi.viewModel.dataBinders.DetailAlbumDataBinder
import ru.exemple.itunesapi.viewModel.detailAlbumViewModel.DetailAlbumViewModel
import ru.exemple.itunesapi.viewModel.detailAlbumViewModel.DetailAlbumViewModelImpl

class DetailAlbumActivity : AppCompatActivity() {

    private lateinit var detailAlbumView: DetailAlbumView
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
            DetailAlbumView(
                findViewById(R.id.activity_detail_album__llDetailAlbum),
                this,
                detailAlbumViewModel,
                layoutInflater,
                intent.getIntExtra(ALBUM_ID_KEY, 0)
            )
    }

    private fun setHomeButtonNavigation() {
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setHomeButtonEnabled(true)
    }
}