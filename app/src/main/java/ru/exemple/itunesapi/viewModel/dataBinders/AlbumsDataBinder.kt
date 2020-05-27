package ru.exemple.itunesapi.viewModel.dataBinders

import ru.exemple.itunesapi.iTunesApi.model.Album
import ru.exemple.itunesapi.viewModel.row.AlbumRow
import java.util.*
import kotlin.collections.ArrayList

class AlbumsDataBinder {

    fun bindAlbumsRows(albums: ArrayList<Album>): ArrayList<AlbumRow> {
        val albumRows = ArrayList<AlbumRow>()
        for (i in 0 until albums.size) {
            val artist = albums[i].artist
            val title = albums[i].title
            val albumPictureUrl = albums[i].albumPictureUrl
            val albumId = albums[i].albumId
            albumRows.add(AlbumRow(artist, title, albumPictureUrl, albumId))
        }
        albumRows.sortWith(Comparator { o1, o2 -> o1.title.compareTo(o2.title) })
        return albumRows
    }
}