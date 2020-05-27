package ru.exemple.itunesapi.viewModel.dataBinders

import ru.exemple.itunesapi.iTunesApi.model.DetailAlbum
import ru.exemple.itunesapi.viewModel.row.DetailAlbumRow
import ru.exemple.itunesapi.viewModel.row.TrackRow
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList

class DetailAlbumDataBinder {

    fun bindDetailAlbumRow(detailAlbum: DetailAlbum): DetailAlbumRow {
        val artist = detailAlbum.artist
        val title = detailAlbum.title
        val albumPictureUrl = detailAlbum.albumPictureUrl
        val trackList = ArrayList<TrackRow>()
        for (i in 0 until detailAlbum.trackList.size) {
            val trackNumber = detailAlbum.trackList[i].number.toString()
            val trackTitle = detailAlbum.trackList[i].title
            val trackTime = trackTimeFormat(detailAlbum.trackList[i].time)
            trackList.add(TrackRow(trackNumber, trackTitle, trackTime))
        }
        return DetailAlbumRow(artist, title, albumPictureUrl, trackList)
    }

    private fun trackTimeFormat(trackTime: Int): String {
        val sf = SimpleDateFormat("hh:mm:ss", Locale.getDefault())
        return sf.format(trackTime)
    }
}