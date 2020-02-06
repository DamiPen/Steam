package com.github.damipen.steam.user

import com.github.damipen.steam.badRequest
import com.github.damipen.steam.fromPath
import okhttp3.mockwebserver.Dispatcher
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.RecordedRequest
import java.net.HttpURLConnection


internal object SteamUsersDispatcher : Dispatcher() {

    override fun dispatch(request: RecordedRequest): MockResponse {
        val requestUrl = request.requestUrl ?: throw Throwable("RequestUrl is null")
        val key = requestUrl.queryParameter("key")
        val steamId = requestUrl.queryParameter("steamid")
        val steamIds = requestUrl.queryParameter("steamids")
        val url = requestUrl.queryParameter("vanityurl")
        return when (requestUrl.encodedPath) {
            "/ISteamUser/GetFriendList/v0001" -> checkGetFriend(key, steamId)
            "/ISteamUser/GetPlayerBans/v0001" -> checkGetPlayerBans(key, steamIds)
            "/ISteamUser/GetPlayerSummaries/v0002" -> checkGetPlayerSummaries(key, steamIds)
            "/ISteamUser/GetUserGroupList/v0001" -> checkGetUserGroupList(key, steamId)
            "/ISteamUser/ResolveVanityURL/v0001" -> checkResolveVanityURL(key, url)
            else -> MockResponse().setResponseCode(HttpURLConnection.HTTP_NOT_FOUND).setBody("Wronf path [${request.requestUrl}]")
        }
    }

    private fun checkGetFriend(key: String?, steamId: String?): MockResponse {
        return if ((key == "") or (steamId == "")) {
            badRequest
        } else {
            MockResponse().fromPath("ISteamUser/GetFriendList.json")
        }
    }

    private fun checkGetPlayerBans(key: String?, steamIds: String?): MockResponse {
        return if ((key == "") or (steamIds == "")) {
            badRequest
        } else {
            MockResponse().fromPath("ISteamUser/GetPlayerBans.json")
        }
    }

    private fun checkGetPlayerSummaries(key: String?, steamIds: String?): MockResponse {
        return if ((key == "") or (steamIds == "")) {
            badRequest
        } else {
            MockResponse().fromPath("ISteamUser/GetPlayerSummaries.json")
        }
    }

    private fun checkGetUserGroupList(key: String?, steamId: String?): MockResponse {
        return if ((key == "") or (steamId == "")) {
            badRequest
        } else {
            MockResponse().fromPath("ISteamUser/GetUserGroupList.json")
        }
    }

    private fun checkResolveVanityURL(key: String?, url: String?): MockResponse {
        println("URL: [$url]")
        return if (key == "") {
            badRequest
        } else {
            when (url) {
                "xiaomi" -> MockResponse().fromPath("ISteamUser/ResolveVanityURL.json")
                else -> MockResponse().fromPath("ISteamUser/ResolveVanityURLError.json")
            }
        }
    }


}
