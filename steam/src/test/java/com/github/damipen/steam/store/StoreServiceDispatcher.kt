package com.github.damipen.steam.store

import com.github.damipen.steam.forbidden
import com.github.damipen.steam.fromPath
import okhttp3.mockwebserver.Dispatcher
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.RecordedRequest
import java.net.HttpURLConnection

internal object StoreServiceDispatcher : Dispatcher() {
    override fun dispatch(request: RecordedRequest): MockResponse {
        val requestUrl = request.requestUrl ?: throw Throwable("RequestUrl is null")
        val key = requestUrl.queryParameter("key")
        val steamId = requestUrl.queryParameter("steamid")
        return when (request.requestUrl?.encodedPath) {
            "/IStoreService/GetAppList/v0001" -> checkGetAppList(key, steamId)
            else -> MockResponse().setResponseCode(HttpURLConnection.HTTP_NOT_FOUND).setBody("Wronf path [${request.requestUrl}]")
        }
    }

    private fun checkGetAppList(key: String?, steamId: String?): MockResponse {
        return if ((key == "") or (steamId == "")) {
            forbidden
        } else {
            MockResponse().fromPath("IStoreService/GetAppList.json")
        }
    }

}