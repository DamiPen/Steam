package com.github.damipen.steam.playerservice

import com.github.damipen.steam.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface PlayerServiceApi {

    @GET("/IPlayerService/GetRecentlyPlayedGames/v0001")
    suspend fun getRecentlyPlayedGames(
        @Query("key") key: String,
        @Query("steamid") steamId: String,
        @Query("count") count: Int = 0
    ): Response<RecentGame>

    @GET("/IPlayerService/GetOwnedGames/v0001")
    suspend fun getOwnedGames(
        @Query("key") key: String,
        @Query("steamid") steamId: String,
        @Query("include_appinfo") includeAppInfo: Boolean = false,
        @Query("include_played_free_games") includePlayedFreeGames: Boolean = false,
        @Query("appids_filter") appIdsFilter: Long? = null
    ): Response<OwnedGames>

    @GET("/IPlayerService/GetSteamLevel/v0001")
    suspend fun getSteamLevel(
        @Query("key") key: String,
        @Query("steamid") steamId: String
    ): Response<PlayerLevel>

    @GET("/IPlayerService/GetBadges/v0001")
    suspend fun getBadges(
        @Query("key") key: String,
        @Query("steamid") steamId: String
    ): Response<BadgeInfo>

    @GET("/IPlayerService/GetCommunityBadgeProgress/v0001")
    suspend fun getCommunityBadgeProgress(
        @Query("key") key: String,
        @Query("steamid") steamId: String,
        @Query("badgeid") badgeId: Long? = null
    ): Response<CommunityBadgeProgress>

    @GET("/IPlayerService/IsPlayingSharedGame/v0001")
    suspend fun isPlayingSharedGame(
        @Query("key") key: String,
        @Query("steamid") steamId: String,
        @Query("appid_playing") appidPlaying: Long? = null
    ): Response<Lender>

}