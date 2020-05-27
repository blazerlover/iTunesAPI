package ru.example.itunesapi.iTunesApi.repository

import okhttp3.OkHttpClient
import okhttp3.Request
import okio.IOException
import org.json.JSONObject

class HttpRequestExecutor {

    fun makeAlbumsRequest(artist: String): JSONObject {
        val urlArtist = formatForUrl(artist)
        val request = buildAlbumsRequest(urlArtist)

        return execute(request)
    }

    fun makeDetailAlbumRequest(albumId: Int): JSONObject {
        val request = buildDetailAlbumRequest(albumId)

        return execute(request)
    }

    private fun execute(request: Request): JSONObject {
        val okHttpClient = OkHttpClient()
        okHttpClient.newCall(request).execute().use { response ->
            if (!response.isSuccessful) {
                throw IOException("Unexpected code $request")
            }
            return JSONObject(response.body!!.string())
        }
    }

    private fun formatForUrl(value: String): String {
        return value.replace(' ', '+')
    }

    private fun buildAlbumsRequest(urlArtist: String): Request {
        return Request.Builder()
            .url("https://itunes.apple.com/search?term=$urlArtist&entity=album")
            .build()
    }

    private fun buildDetailAlbumRequest(albumId: Int): Request {
        return Request.Builder()
            .url("https://itunes.apple.com/lookup?id=$albumId&entity=song")
            .build()
    }
}