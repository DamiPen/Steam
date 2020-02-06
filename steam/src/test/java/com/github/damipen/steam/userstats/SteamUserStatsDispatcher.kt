package com.github.damipen.steam.userstats

import com.github.damipen.steam.badRequest
import com.github.damipen.steam.fromPath
import okhttp3.mockwebserver.Dispatcher
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.RecordedRequest
import java.net.HttpURLConnection

internal object SteamUserStatsDispatcher : Dispatcher() {

    override fun dispatch(request: RecordedRequest): MockResponse {
        val key = request.requestUrl?.queryParameter("key")
        val gameId = request.requestUrl?.queryParameter("gameid")
        val appId = request.requestUrl?.queryParameter("appid")
        val steamId = request.requestUrl?.queryParameter("steamid")
        return when (request.requestUrl?.encodedPath) {
            "/ISteamUserStats/GetGlobalAchievementPercentagesForApp/v0002" -> checkGetGlobalAchievementPercentagesForApp(gameId)
            "/ISteamUserStats/GetNumberOfCurrentPlayers/v0001" -> checkGetNumberOfCurrentPlayers(gameId)
            "/ISteamUserStats/GetPlayerAchievements/v0001" -> checkGetPlayerAchievements(key, appId, steamId)
            "/ISteamUserStats/GetSchemaForGame/v0002" -> checkGetSchemaForGame(key, appId)
            "/ISteamUserStats/GetUserStatsForGame/v0002" -> checkGetUserStatsForGame(key, appId, steamId)
            else -> MockResponse().setResponseCode(HttpURLConnection.HTTP_NOT_FOUND).setBody("Wronf path [${request.requestUrl}]")
        }
    }

    private fun checkGetGlobalAchievementPercentagesForApp(gameId: String?): MockResponse {
        return if (gameId == "") {
            badRequest
        } else {
            MockResponse().fromPath("ISteamUserStats/GetGlobalAchievementPercentagesForApp.json")
        }
    }

    private fun checkGetNumberOfCurrentPlayers(gameId: String?): MockResponse {
        return if (gameId == "") {
            badRequest
        } else {
            MockResponse().fromPath("ISteamUserStats/GetNumberOfCurrentPlayers.json")
        }
    }

    private fun checkGetPlayerAchievements(key: String?, appId: String?, steamId: String?): MockResponse {
        return if ((key == "") or (appId == "") or (steamId == "")) {
            badRequest
        } else {
            MockResponse().fromPath("ISteamUserStats/GetPlayerAchievements.json")
        }
    }

    private fun checkGetSchemaForGame(key: String?, appId: String?): MockResponse {
        return if ((key == "") or (appId == "")) {
            badRequest
        } else {
            MockResponse().fromPath("ISteamUserStats/GetSchemaForGame.json")
        }
    }

    private fun checkGetUserStatsForGame(key: String?, appId: String?, steamId: String?): MockResponse {
        return if ((key == "") or (appId == "") or (steamId == "")) {
            badRequest
        } else {
            MockResponse().fromPath("ISteamUserStats/GetUserStatsForGame.json")
        }
    }
}