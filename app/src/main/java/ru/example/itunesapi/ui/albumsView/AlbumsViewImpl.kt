package ru.example.itunesapi.ui.albumsView

import android.view.View
import android.widget.ProgressBar
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import ru.example.itunesapi.R
import ru.example.itunesapi.viewModel.row.AlbumRow

class AlbumsViewImpl : AlbumsAdapter.Listener {

    interface OnAlbumsAdapterItemClickListener {
        fun onAlbumsAdapterItemClicked(albumId: Int)
    }

    constructor(
        baseRootView: View,
        onAlbumsAdapterItemClickListener: OnAlbumsAdapterItemClickListener
    ) {
        this.baseRootView = baseRootView
        this.onAlbumsAdapterItemClickListener = onAlbumsAdapterItemClickListener
        init()
    }

    private var baseRootView: View
    private var onAlbumsAdapterItemClickListener: OnAlbumsAdapterItemClickListener
    private lateinit var albumsAdapter: AlbumsAdapter

    private lateinit var recyclerView: RecyclerView

    fun showData(albums: ArrayList<AlbumRow>) {
        initAlbums(albums)
    }

    fun clearData() {
        initEmptyAlbums()
    }

    override fun onAlbumsAdapterItemClicked(albumId: Int) {
        onAlbumsAdapterItemClickListener.onAlbumsAdapterItemClicked(albumId)
    }

    private fun init() {
        recyclerView = baseRootView.findViewById(R.id.activity_main__rvAlbums)
        albumsAdapter = AlbumsAdapter(this)
        val gridLayoutManager = GridLayoutManager(baseRootView.context, 2)
        recyclerView.layoutManager = gridLayoutManager
        recyclerView.adapter = albumsAdapter
    }

    private fun initAlbums(albumRows: ArrayList<AlbumRow>) {
        albumsAdapter.notifyData(albumRows)
    }

    private fun initEmptyAlbums() {
        albumsAdapter.clearData()
    }
}