package com.github.damipen.steam.steamapp

class SteamAppUsecase(
    val api: SteamAppApi
) {

    suspend fun getAppList(): List<Apps.App> {
        return api.getAppList().apps.appsList
    }

}