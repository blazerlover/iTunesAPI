package ru.example.itunesapi.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import ru.example.itunesapi.ALBUM_ID_KEY
import ru.example.itunesapi.App
import ru.example.itunesapi.R
import ru.example.itunesapi.databinding.ActivityDetailAlbumBinding
import ru.example.itunesapi.databinding.TrackRowBinding
import ru.example.itunesapi.viewModel.dataBinders.DetailAlbumDataBinder
import ru.example.itunesapi.viewModel.detailAlbumViewModel.DetailAlbumViewModelImpl
import ru.example.itunesapi.viewModel.row.DetailAlbumRow

class DetailAlbumActivity : AppCompatActivity() {

    private lateinit var liveData: MutableLiveData<DetailAlbumRow>
    private lateinit var detailAlbumViewModel: DetailAlbumViewModelImpl

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail_album)
        setHomeButtonNavigation()
        init()
    }

    private fun init() {
        val activityDetailAlbumBinding: ActivityDetailAlbumBinding? =
            DataBindingUtil.setContentView(this, R.layout.activity_detail_album)
        val api = (application as App).getDependencyRoot().api
        val detailAlbumDataBinder = DetailAlbumDataBinder()
        val factory =
            DetailAlbumViewModelImpl.DetailAlbumViewModelFactory(api, detailAlbumDataBinder)
        detailAlbumViewModel =
            ViewModelProvider(this, factory).get(DetailAlbumViewModelImpl::class.java)
        liveData = detailAlbumViewModel.getDetailAlbumRowLiveData()
        liveData.observe(
            this,
            Observer {
                bindViewData(activityDetailAlbumBinding, detailAlbumViewModel)
            })
        bindViewData(activityDetailAlbumBinding, detailAlbumViewModel)
        detailAlbumViewModel.getDetailAlbum(intent.getIntExtra(ALBUM_ID_KEY, 0))
    }

    private fun setHomeButtonNavigation() {
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }

    private fun bindViewData(
        activityDetailAlbumBinding: ActivityDetailAlbumBinding?,
        detailAlbumViewModelImpl: DetailAlbumViewModelImpl
    ) {
        activityDetailAlbumBinding?.detailAlbumViewModel = detailAlbumViewModelImpl
        val detailAlbumRow = detailAlbumViewModelImpl.getDetailAlbumRowLiveData().value ?: return
        for (trackRow in (detailAlbumRow).trackList) {
            val trackRowBinding = TrackRowBinding.inflate(
                layoutInflater,
                findViewById(R.id.activity_detail_album__llTrackList),
                true
            )
            trackRowBinding.trackRow = trackRow
        }
    }
}