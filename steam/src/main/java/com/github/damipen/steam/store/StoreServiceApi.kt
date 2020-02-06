package com.github.damipen.steam.store

import com.github.damipen.steam.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface StoreServiceApi {

    /**
     * Get all list
     *
     * @param key Access key
     * @param ifModifiedSince Return only items that have been modified since this date (in second)
     * @param haveDescriptionLanguage Return only items that have a description in this language.
     * @param includeGames Include games (defaults to enabled)
     * @param includeDlc Include DLC
     * @param includeSoftware Include software items
     * @param includeVideos Include videos and series
     * @param includeHardware Include hardware
     * @param lastAppId For continuations, this is the last appid returned from the previous call.
     * @param maxResults Number of results to return at a time.  Default 10k, max 50k.
     */
    @GET("/IStoreService/GetAppList/v0001")
    suspend fun getAppList(
        @Query("key") key: String,
        @Query("if_modified_since") ifModifiedSince: Long? = null,
        @Query("have_description_language") haveDescriptionLanguage: String? = null,
        @Query("include_games") includeGames: Boolean = true,
        @Query("include_dlc") includeDlc: Boolean = false,
        @Query("include_software") includeSoftware: Boolean = false,
        @Query("include_videos") includeVideos: Boolean = false,
        @Query("include_hardware") includeHardware: Boolean = false,
        @Query("last_appid") lastAppId: Long? = null,
        @Query("max_results") maxResults: Long = 10000L
    ): Response<Apps>
}