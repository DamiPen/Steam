package com.github.damipen.steam

import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

private const val INTERFACE = "IStoreService"

interface IStoreService {

    @GET("$INTERFACE/GetAppList")
    suspend fun getAppList(
        @Query("key") key: String,
        @Query("if_modified_since") if_modified_since: Long? = null,
        @Query("have_description_language") have_description_language: String? = null,
        @Query("include_games") include_games: Boolean = true,
        @Query("include_dlc") include_dlc: Boolean = false,
        @Query("include_software") include_software: Boolean = false,
        @Query("include_videos") include_videos: Boolean = false,
        @Query("include_hardware") include_hardware: Boolean = false,
        @Query("last_appid") last_appid: Long? = null,
        @Query("max_results") max_results: Long = 10000L
    ) : Response<String>
}