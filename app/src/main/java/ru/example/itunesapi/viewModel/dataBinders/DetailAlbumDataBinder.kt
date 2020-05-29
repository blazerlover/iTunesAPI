package ru.example.itunesapi.viewModel.dataBinders

import ru.example.itunesapi.iTunesApi.model.DetailAlbum
import ru.example.itunesapi.viewModel.row.DetailAlbumRow
import ru.example.itunesapi.viewModel.row.TrackRow
import java.text.SimpleDateFormat
import java.util.*
import java.util.concurrent.TimeUnit
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
        return String.format("%02d:%02d",
            TimeUnit.MILLISECONDS.toMinutes(trackTime.toLong()),
            TimeUnit.MILLISECONDS.toSeconds(trackTime.toLong()) -
                    TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(trackTime.toLong()))
        )
    }
}