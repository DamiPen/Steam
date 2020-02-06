package com.github.damipen.steam.steamapp

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class AppList(@Json(name = "applist") val apps: Apps)

@JsonClass(generateAdapter = true)
data class Apps(@Json(name = "apps") val appsList: List<App>) {

    @JsonClass(generateAdapter = true)
    data class App(
        @Json(name = "appid") val appId: Long,
        @Json(name = "name") val name: String

    )
}

