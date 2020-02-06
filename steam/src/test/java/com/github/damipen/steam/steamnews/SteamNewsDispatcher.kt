package com.github.damipen.steam.steamnews

import com.github.damipen.steam.badRequest
import com.github.damipen.steam.fromPath
import okhttp3.mockwebserver.Dispatcher
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.RecordedRequest
import java.net.HttpURLConnection

internal object SteamNewsDispatcher : Dispatcher() {

    override fun dispatch(request: RecordedRequest): MockResponse {
        return when (request.requestUrl?.encodedPath) {
            "/IStoreService/GetNewsForApp/v0002" -> checkGetNewsForApp(request.requestUrl?.queryParameter("appid"))
            else -> MockResponse().setResponseCode(HttpURLConnection.HTTP_NOT_FOUND).setBody("Wronf path [${request.requestUrl}]")
        }
    }

    private fun checkGetNewsForApp(appid: String?): MockResponse {
        return if (appid == "") {
            badRequest
        } else {
            MockResponse().fromPath("ISteamNews/GetNewsForApp.json")
        }
    }


}