package ru.exemple.itunesapi.ui.detailAlbumView

import android.view.LayoutInflater
import android.view.View
import android.widget.*
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import com.squareup.picasso.Picasso
import ru.exemple.itunesapi.R
import ru.exemple.itunesapi.ui.BaseView
import ru.exemple.itunesapi.viewModel.detailAlbumViewModel.DetailAlbumViewModel
import ru.exemple.itunesapi.viewModel.row.DetailAlbumRow
import ru.exemple.itunesapi.viewModel.row.TrackRow

class DetailAlbumView : BaseView {

    constructor(
        baseRootView: View,
        lifecycleOwner: LifecycleOwner,
        detailAlbumViewModel: DetailAlbumViewModel,
        layoutInflater: LayoutInflater,
        albumId: Int
    ) {
        this.baseRootView = baseRootView
        this.lifecycleOwner = lifecycleOwner
        this.detailAlbumViewModel = detailAlbumViewModel
        this.layoutInflater = layoutInflater
        this.albumId = albumId
        init()
    }

    private var baseRootView: View
    private var lifecycleOwner: LifecycleOwner
    private var detailAlbumViewModel: DetailAlbumViewModel
    private var layoutInflater: LayoutInflater
    private var albumId: Int

    private lateinit var liveData: MutableLiveData<DetailAlbumRow>

    private lateinit var pbLoading: ProgressBar
    private lateinit var svDetailAlbum: ScrollView
    private lateinit var ivAlbum: ImageView
    private lateinit var tvAlbum: TextView
    private lateinit var tvArtist: TextView
    private lateinit var llTrackList: LinearLayout

    override fun showData() {
        hideLoading()
        initDetailAlbum(liveData.value!!)
    }

    override fun showLoading() {
        pbLoading.visibility = View.VISIBLE
        svDetailAlbum.visibility = View.INVISIBLE
    }

    override fun hideLoading() {
        pbLoading.visibility = View.GONE
        svDetailAlbum.visibility = View.VISIBLE
    }

    private fun init() {
        liveData = detailAlbumViewModel.getLiveData()
        liveData.observe(lifecycleOwner, Observer { showData() })
        pbLoading = baseRootView.findViewById(R.id.activity_detail_album__pbLoading)
        svDetailAlbum = baseRootView.findViewById(R.id.activity_detail_album__svDetailAlbum)
        ivAlbum = baseRootView.findViewById(R.id.activity_detail_album__ivAlbum)
        tvAlbum = baseRootView.findViewById(R.id.activity_detail_album__tvAlbum)
        tvArtist = baseRootView.findViewById(R.id.activity_detail_album__tvArtist)
        llTrackList = baseRootView.findViewById(R.id.activity_detail_album__llTrackList)
        detailAlbumViewModel.getDetailAlbum(albumId)
        showLoading()
    }

    private fun initDetailAlbum(detailAlbumRow: DetailAlbumRow) {
        initImageViewAlbum(detailAlbumRow.albumPictureUrl)
        tvAlbum.text = detailAlbumRow.title
        tvArtist.text = detailAlbumRow.artist
        initTrackList(detailAlbumRow.trackList)
    }

    private fun initImageViewAlbum(albumPictureUrl: String) {
        Picasso.get()
            .load(albumPictureUrl)
//            .resizeDimen(500, 500)
            .resize(500, 500)
            .centerCrop()
            .into(ivAlbum)
    }

    private fun initTrackList(trackList: ArrayList<TrackRow>) {
        var tvNumber: TextView
        var tvTitle: TextView
        var tvTime: TextView
        for (i in 0 until trackList.size) {
            val trackView = layoutInflater.inflate(
                R.layout.track_row,
                baseRootView.findViewById(R.id.activity_detail_album__llTrackList),
                false
            )
            tvNumber = trackView.findViewById(R.id.track_row__tvNumber)
            tvNumber.text = trackList[i].number
            tvTitle = trackView.findViewById(R.id.track_row__tvTrack)
            tvTitle.text = trackList[i].title
            llTrackList.addView(trackView)
            tvTime = trackView.findViewById(R.id.track_row__tvTime)
            tvTime.text = trackList[i].time
        }
    }
}