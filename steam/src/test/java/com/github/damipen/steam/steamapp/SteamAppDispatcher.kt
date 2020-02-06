package com.github.damipen.steam.steamapp

import com.github.damipen.steam.fromPath
import okhttp3.mockwebserver.Dispatcher
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.RecordedRequest

internal object SteamAppDispatcher : Dispatcher() {
    override fun dispatch(request: RecordedRequest): MockResponse {
        return MockResponse().fromPath("ISteamApps/GetAppList.json")
    }

}