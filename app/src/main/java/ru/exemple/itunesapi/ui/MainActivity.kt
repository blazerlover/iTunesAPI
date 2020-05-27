package ru.exemple.itunesapi.ui

import android.app.SearchManager
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.Menu
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import ru.exemple.itunesapi.ALBUM_ID_KEY
import ru.exemple.itunesapi.App
import ru.exemple.itunesapi.R
import ru.exemple.itunesapi.ui.albumsView.AlbumsAdapter
import ru.exemple.itunesapi.ui.albumsView.AlbumsView
import ru.exemple.itunesapi.viewModel.albumsViewModel.AlbumsViewModel
import ru.exemple.itunesapi.viewModel.albumsViewModel.AlbumsViewModelImpl
import ru.exemple.itunesapi.viewModel.dataBinders.AlbumsDataBinder

class MainActivity : AppCompatActivity(), AlbumsAdapter.Listener {

    private lateinit var albumsView: AlbumsView
    private lateinit var albumsViewModel: AlbumsViewModel

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
        val gridLayoutManager = GridLayoutManager(this, 2)
        val factory = AlbumsViewModelImpl.AlbumsViewModelFactory(api, albumsDataBinder)
        val albumsAdapter =
            AlbumsAdapter(this)
        albumsViewModel =
            ViewModelProvider(this, factory).get(AlbumsViewModelImpl::class.java)
        albumsView = AlbumsView(
            findViewById(R.id.activity_main__llAlbums),
            this,
            albumsViewModel,
            albumsAdapter,
            gridLayoutManager
        )
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