package ru.exemple.itunesapi.ui.albumsView

import android.view.View
import android.widget.ProgressBar
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import ru.exemple.itunesapi.R
import ru.exemple.itunesapi.ui.BaseView
import ru.exemple.itunesapi.viewModel.albumsViewModel.AlbumsViewModel
import ru.exemple.itunesapi.viewModel.row.AlbumRow

class AlbumsView : BaseView {

    constructor(
        baseRootView: View,
        lifecycleOwner: LifecycleOwner,
        albumsViewModel: AlbumsViewModel,
        albumsAdapter: AlbumsAdapter,
        gridLayoutManager: GridLayoutManager
    ) {
        this.baseRootView = baseRootView
        this.lifecycleOwner = lifecycleOwner
        this.albumsViewModel = albumsViewModel
        this.albumsAdapter = albumsAdapter
        this.gridLayoutManager = gridLayoutManager
        init()
    }

    private var baseRootView: View
    private var albumsViewModel: AlbumsViewModel
    private var lifecycleOwner: LifecycleOwner
    private var albumsAdapter: AlbumsAdapter
    private var gridLayoutManager: GridLayoutManager

    private lateinit var liveData: MutableLiveData<ArrayList<AlbumRow>>
    private lateinit var pbLoading: ProgressBar
    private lateinit var recyclerView: RecyclerView

    override fun showData() {
        hideLoading()
        initAlbums(liveData.value!!)
    }

    override fun showLoading() {
        recyclerView.visibility = View.INVISIBLE
        pbLoading.visibility = View.VISIBLE
    }

    override fun hideLoading() {
        pbLoading.visibility = View.GONE
        recyclerView.visibility = View.VISIBLE
    }

    fun onTextQueryReady(textQuery: String) {
        showLoading()
        albumsViewModel.getAlbums(textQuery)
    }

    private fun init() {
        liveData = albumsViewModel.getLiveData()
        liveData.observe(lifecycleOwner, Observer { showData() })
        pbLoading = baseRootView.findViewById(R.id.activity_main__pbLoading)
        recyclerView = baseRootView.findViewById(R.id.activity_main__rvAlbums)
        recyclerView.layoutManager = gridLayoutManager
        recyclerView.adapter = albumsAdapter
    }

    private fun initAlbums(albumRows: ArrayList<AlbumRow>) {
        albumsAdapter.notifyData(albumRows)
    }
}