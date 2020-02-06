package com.github.damipen.steam.playerservice

import com.github.damipen.steam.forbidden
import com.github.damipen.steam.fromPath
import com.github.damipen.steam.internalError
import com.github.damipen.steam.unauthorized
import okhttp3.mockwebserver.Dispatcher
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.RecordedRequest
import java.net.HttpURLConnection

internal object PlayerServiceDispatcher : Dispatcher() {
    override fun dispatch(request: RecordedRequest): MockResponse {
        val requestUrl = request.requestUrl ?: throw Throwable("RequestUrl is null")
        val key = requestUrl.queryParameter("key")
        val steamId = requestUrl.queryParameter("steamid")
        return when (request.requestUrl?.encodedPath) {
            "/IPlayerService/GetRecentlyPlayedGames/v0001" -> checkGetRecentlyPlayedGames(key, steamId)
            "/IPlayerService/GetOwnedGames/v0001" -> checkGetOwnedGames(key, steamId)
            "/IPlayerService/GetSteamLevel/v0001" -> checkGetSteamLevel(key, steamId)
            "/IPlayerService/GetBadges/v0001" -> checkGetBadges(key, steamId)
            "/IPlayerService/GetCommunityBadgeProgress/v0001" -> checkGetCommunityBadgeProgress(key, steamId)
            "/IPlayerService/IsPlayingSharedGame/v0001" -> checkIsPlayingSharedGame(key, steamId)
            else -> MockResponse().setResponseCode(HttpURLConnection.HTTP_NOT_FOUND).setBody("Wronf path [${request.requestUrl}]")
        }
    }

    private fun checkGetRecentlyPlayedGames(key: String?, steamId: String?): MockResponse {
        return if (key == "") {
            forbidden
        } else if (steamId == "") {
            internalError
        } else {
            MockResponse().fromPath("IPlayerService/GetRecentlyPlayedGames.json")
        }
    }

    private fun checkGetOwnedGames(key: String?, steamId: String?): MockResponse {
        return if (key == "") {
            unauthorized
        } else if (steamId == "") {
            internalError
        } else {
            MockResponse().fromPath("IPlayerService/GetOwnedGames.json")
        }
    }

    private fun checkGetSteamLevel(key: String?, steamId: String?): MockResponse {
        return if (key == "") {
            forbidden
        } else if (steamId == "") {
            internalError
        } else {
            MockResponse().fromPath("IPlayerService/GetSteamLevel.json")
        }
    }

    private fun checkGetBadges(key: String?, steamId: String?): MockResponse {
        return if (key == "") {
            forbidden
        } else if (steamId == "") {
            internalError
        } else {
            MockResponse().fromPath("IPlayerService/GetBadges.json")
        }
    }

    private fun checkGetCommunityBadgeProgress(key: String?, steamId: String?): MockResponse {
        return if (key == "") {
            forbidden
        } else if (steamId == "") {
            internalError
        } else {
            MockResponse().fromPath("IPlayerService/GetCommunityBadgeProgress.json")
        }
    }

    private fun checkIsPlayingSharedGame(key: String?, steamId: String?): MockResponse {
        return if (key == "") {
            forbidden
        } else if (steamId == "") {
            internalError
        } else {
            MockResponse().fromPath("IPlayerService/IsPlayingSharedGame.json")
        }
    }
}