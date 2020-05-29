package ru.example.itunesapi.ui

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import ru.example.itunesapi.ALBUM_ID_KEY
import ru.example.itunesapi.App
import ru.example.itunesapi.ENTER_ARTIST_NAME
import ru.example.itunesapi.R
import ru.example.itunesapi.databinding.ActivityMainBinding
import ru.example.itunesapi.ui.albumsView.AlbumsViewImpl
import ru.example.itunesapi.viewModel.albumsViewModel.AlbumsViewModelImpl
import ru.example.itunesapi.viewModel.dataBinders.AlbumsDataBinder
import ru.example.itunesapi.viewModel.row.AlbumRow

class MainActivity : AppCompatActivity(), AlbumsViewImpl.OnAlbumsAdapterItemClickListener {

    private lateinit var albumsViewImpl: AlbumsViewImpl
    private lateinit var albumsViewModel: AlbumsViewModelImpl
    private lateinit var liveData: MutableLiveData<ArrayList<AlbumRow>>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        init()
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.activity_main_menu, menu)

        val searchItem = menu?.findItem(R.id.activity_main_menu__search)
        val searchView = searchItem?.actionView as SearchView
        searchView.setIconifiedByDefault(false)
        searchView.requestFocus()
        searchView.queryHint = ENTER_ARTIST_NAME

        addSearchViewListener(searchView)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onAlbumsAdapterItemClicked(albumId: Int) {
        val intent = Intent(this, DetailAlbumActivity::class.java)
        intent.putExtra(ALBUM_ID_KEY, albumId)
        startActivity(intent)
    }

    private fun init() {
        val activityMainBinding: ActivityMainBinding? =
            DataBindingUtil.setContentView(this, R.layout.activity_main)
        val api = (application as App).getDependencyRoot().api
        val albumsDataBinder = AlbumsDataBinder()
        val factory = AlbumsViewModelImpl.AlbumsViewModelFactory(api, albumsDataBinder)
        albumsViewModel =
            ViewModelProvider(this, factory).get(AlbumsViewModelImpl::class.java)
        activityMainBinding?.albumViewModel = albumsViewModel
        albumsViewImpl = AlbumsViewImpl(
            findViewById(R.id.activity_main__llAlbums),
            this
        )
        liveData = albumsViewModel.getLiveData()
        liveData.observe(this, Observer { albums -> albumsViewImpl.showData(albums) })
    }

    private fun addSearchViewListener(searchView: SearchView) {
        searchView.setOnQueryTextListener(object :
            SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String): Boolean {
                return true
            }

            override fun onQueryTextChange(newText: String): Boolean {
                callSearch(newText)
                return false
            }

            fun callSearch(query: String?) {
                if (query == null) {
                    return
                }
                albumsViewModel.getAlbums(query)
            }
        })

        searchView.setOnCloseListener {
            albumsViewImpl.clearData()
            true
        }
    }
}