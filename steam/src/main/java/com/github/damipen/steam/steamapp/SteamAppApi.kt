package com.github.damipen.steam.steamapp

import retrofit2.http.GET

interface SteamAppApi {

    @GET("/ISteamApps/GetAppList/v0002")
    suspend fun getAppList() : AppList

}