package ru.example.itunesapi

import android.content.Context
import ru.example.itunesapi.iTunesApi.ApiImpl
import ru.example.itunesapi.iTunesApi.repository.HttpRequestExecutor
import ru.example.itunesapi.iTunesApi.util.JsonParser

class DependencyRoot(context: Context) {
    private val httpRequestExecutor = HttpRequestExecutor()
    private val jsonParser = JsonParser()
    val api = ApiImpl(jsonParser, httpRequestExecutor)
}