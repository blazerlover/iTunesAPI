package ru.example.itunesapi.ui.detailAlbumView

import android.view.LayoutInflater
import android.view.View
import android.widget.*
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import com.squareup.picasso.Picasso
import ru.example.itunesapi.R
import ru.example.itunesapi.ui.Loading
import ru.example.itunesapi.viewModel.detailAlbumViewModel.DetailAlbumViewModel
import ru.example.itunesapi.viewModel.row.DetailAlbumRow
import ru.example.itunesapi.viewModel.row.TrackRow

class DetailAlbumViewImpl : DetailAlbumView {

    constructor(
        baseRootView: View,
        listener: DetailAlbumView.Listener,
        layoutInflater: LayoutInflater,
        albumId: Int
    ) {
        this.baseRootView = baseRootView
        this.listener = listener
        this.layoutInflater = layoutInflater
        this.albumId = albumId
        init()
    }

    private var baseRootView: View
    private var listener: DetailAlbumView.Listener
    private var layoutInflater: LayoutInflater
    private var albumId: Int

    private lateinit var liveData: MutableLiveData<DetailAlbumRow>

    private lateinit var pbLoading: ProgressBar
    private lateinit var svDetailAlbum: ScrollView
    private lateinit var ivAlbum: ImageView
    private lateinit var tvAlbum: TextView
    private lateinit var tvArtist: TextView
    private lateinit var llTrackList: LinearLayout

    override fun showData(detailAlbumRow: DetailAlbumRow) {
        hideLoading()
        initDetailAlbum(detailAlbumRow)
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
        pbLoading = baseRootView.findViewById(R.id.activity_detail_album__pbLoading)
        svDetailAlbum = baseRootView.findViewById(R.id.activity_detail_album__svDetailAlbum)
        ivAlbum = baseRootView.findViewById(R.id.activity_detail_album__ivAlbum)
        tvAlbum = baseRootView.findViewById(R.id.activity_detail_album__tvAlbum)
        tvArtist = baseRootView.findViewById(R.id.activity_detail_album__tvArtist)
        llTrackList = baseRootView.findViewById(R.id.activity_detail_album__llTrackList)
        listener.onDetailAlbumViewReady(albumId)
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