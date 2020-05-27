package ru.exemple.itunesapi

import android.content.Context
import ru.exemple.itunesapi.iTunesApi.ApiImpl
import ru.exemple.itunesapi.iTunesApi.repository.HttpRequestExecutor
import ru.exemple.itunesapi.iTunesApi.util.JsonParser

class DependencyRoot(context: Context) {
    private val httpRequestExecutor = HttpRequestExecutor()
    private val jsonParser = JsonParser()
    val api = ApiImpl(jsonParser, httpRequestExecutor)
}