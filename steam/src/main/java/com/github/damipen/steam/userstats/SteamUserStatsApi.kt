package com.github.damipen.steam.userstats

import com.github.damipen.steam.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface SteamUserStatsApi {

    @GET("/ISteamUserStats/GetGlobalAchievementPercentagesForApp/v0002")
    suspend fun getGlobalAchievementPercentagesForApp(
        @Query("gameid") gameId: Long
    ): AchievementPercentages

    @GET("/ISteamUserStats/GetNumberOfCurrentPlayers/v0001")
    suspend fun getNumberOfCurrentPlayers(
        @Query("appid") appId: Long
    ): Response<PlayerCount>

    @GET("/ISteamUserStats/GetPlayerAchievements/v0001")
    suspend fun getPlayerAchievements(
        @Query("key") key: String,
        @Query("steamid") steamId: String,
        @Query("appid") appId: Long,
        @Query("l") lang: String? = null
    ): PlayerStats<PlayerStats.PlayerGame.PlayerAchievement>

    @GET("/ISteamUserStats/GetSchemaForGame/v0002")
    suspend fun getSchemaForGame(
        @Query("key") key: String,
        @Query("appid") appId: Long,
        @Query("l") lang: String? = null
    ): Game

    @GET("/ISteamUserStats/GetUserStatsForGame/v0002")
    suspend fun getUserStatsForGame(
        @Query("key") key: String,
        @Query("steamid") steamId: String,
        @Query("appid") appId: Long
    ): PlayerStats<PlayerStats.PlayerGame.UserAchievement>


}