package com.github.damipen.steam.steamnews

import retrofit2.http.GET
import retrofit2.http.Query

interface SteamNewsApi {

    @GET("/IStoreService/GetNewsForApp/v0002")
    suspend fun getNewsForApp(
        @Query("appid") appid: String,
        @Query("maxlength") maxLength: Int? = null,
        @Query("enddate") endDate: Long? = null,
        @Query("count") count: Int? = null,
        @Query("feeds") feeds: String? = null,
        @Query("tags") tags: String? = null
    ): AppNewsResponse
}