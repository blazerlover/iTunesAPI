package ru.example.itunesapi.ui.albumsView

import android.view.View
import android.widget.ProgressBar
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import ru.example.itunesapi.R
import ru.example.itunesapi.viewModel.row.AlbumRow

class AlbumsViewImpl : AlbumView, AlbumsAdapter.Listener {

    constructor(
        baseRootView: View,
        albumViewListener: AlbumView.Listener,
        onAlbumsAdapterItemClickListener: AlbumView.OnAlbumsAdapterItemClickListener
    ) {
        this.baseRootView = baseRootView
        this.albumViewListener = albumViewListener
        this.onAlbumsAdapterItemClickListener = onAlbumsAdapterItemClickListener
        init()
    }

    private var baseRootView: View
    private var albumViewListener: AlbumView.Listener
    private var onAlbumsAdapterItemClickListener: AlbumView.OnAlbumsAdapterItemClickListener
    private lateinit var albumsAdapter: AlbumsAdapter

    private lateinit var pbLoading: ProgressBar
    private lateinit var recyclerView: RecyclerView

    override fun showData(albums: ArrayList<AlbumRow>) {
        hideLoading()
        initAlbums(albums)
    }

    override fun showLoading() {
        recyclerView.visibility = View.INVISIBLE
        pbLoading.visibility = View.VISIBLE
    }

    override fun hideLoading() {
        pbLoading.visibility = View.GONE
        recyclerView.visibility = View.VISIBLE
    }

    override fun onAlbumsAdapterItemClicked(albumId: Int) {
        onAlbumsAdapterItemClickListener.onAlbumsAdapterItemClicked(albumId)
    }

    fun onTextQueryReady(textQuery: String) {
        showLoading()
        albumViewListener.onTextQueryReady(textQuery)
    }

    private fun init() {
        pbLoading = baseRootView.findViewById(R.id.activity_main__pbLoading)
        recyclerView = baseRootView.findViewById(R.id.activity_main__rvAlbums)
        albumsAdapter = AlbumsAdapter(this)
        val gridLayoutManager = GridLayoutManager(baseRootView.context, 2)
        recyclerView.layoutManager = gridLayoutManager
        recyclerView.adapter = albumsAdapter
    }

    private fun initAlbums(albumRows: ArrayList<AlbumRow>) {
        albumsAdapter.notifyData(albumRows)
    }
}