package ru.example.itunesapi.ui

import android.app.SearchManager
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.Menu
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import ru.example.itunesapi.ALBUM_ID_KEY
import ru.example.itunesapi.App
import ru.example.itunesapi.R
import ru.example.itunesapi.ui.albumsView.AlbumView
import ru.example.itunesapi.ui.albumsView.AlbumsAdapter
import ru.example.itunesapi.ui.albumsView.AlbumsViewImpl
import ru.example.itunesapi.viewModel.albumsViewModel.AlbumsViewModel
import ru.example.itunesapi.viewModel.albumsViewModel.AlbumsViewModelImpl
import ru.example.itunesapi.viewModel.dataBinders.AlbumsDataBinder
import ru.example.itunesapi.viewModel.row.AlbumRow

class MainActivity : AppCompatActivity(), AlbumView.OnAlbumsAdapterItemClickListener {

    private lateinit var albumsView: AlbumsViewImpl
    private lateinit var albumsViewModel: AlbumsViewModel
    private lateinit var liveData: MutableLiveData<ArrayList<AlbumRow>>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        init()
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.activity_main_menu, menu)

        val searchItem = menu?.findItem(R.id.activity_main_menu__search)
        val searchManager = getSystemService(Context.SEARCH_SERVICE) as SearchManager
        val searchView = searchItem?.actionView as SearchView

        searchView.setSearchableInfo(searchManager.getSearchableInfo(componentName))

        addListener(searchView)
        return super.onCreateOptionsMenu(menu)
    }

    private fun init() {
        val api = (application as App).getDependencyRoot().api
        val albumsDataBinder = AlbumsDataBinder()
        val factory = AlbumsViewModelImpl.AlbumsViewModelFactory(api, albumsDataBinder)
        albumsViewModel =
            ViewModelProvider(this, factory).get(AlbumsViewModelImpl::class.java)

        albumsView = AlbumsViewImpl(
            findViewById(R.id.activity_main__llAlbums),
            albumsViewModel,
            this
        )
        liveData = albumsViewModel.getLiveData()
        liveData.observe(this, Observer { albums -> albumsView.showData(albums) })
    }

    private fun addListener(searchView: SearchView) {
        searchView.setOnQueryTextListener(object :
            SearchView.OnQueryTextListener {

            override fun onQueryTextSubmit(query: String): Boolean {
                callSearch(query)
                return true
            }

            override fun onQueryTextChange(newText: String): Boolean {
//                callSearch(newText)
                return false
            }

            fun callSearch(query: String?) {
                if (query == null) {
                    return
                }
                albumsView.onTextQueryReady(query)
            }
        })
    }

    override fun onAlbumsAdapterItemClicked(albumId: Int) {
        val intent = Intent(this, DetailAlbumActivity::class.java)
        intent.putExtra(ALBUM_ID_KEY, albumId)
        startActivity(intent)
    }
}