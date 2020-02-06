package com.github.damipen.steam.user

import com.github.damipen.steam.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface SteamUserApi {

    @GET("/ISteamUser/GetFriendList/v0001")
    suspend fun getFriendList(
        @Query("key") key: String,
        @Query("steamid") steamId: String,
        @Query("relationship") relationship: String? = null
    ): FriendsList

    @GET("/ISteamUser/GetPlayerBans/v0001")
    suspend fun getPlayerBans(
        @Query("key") key: String,
        @Query("steamids") steamIds: String
    ): Players<PlayerBanInfo>

    @GET("/ISteamUser/GetPlayerSummaries/v0002")
    suspend fun getPlayerSummaries(
        @Query("key") key: String,
        @Query("steamids") steamIds: String
    ): Response<Players<PlayerInfo>>

    @GET("/ISteamUser/GetUserGroupList/v0001")
    suspend fun getUserGroupList(
        @Query("key") key: String,
        @Query("steamid") steamId: String
    ): Response<Groups>

    @GET("/ISteamUser/ResolveVanityURL/v0001")
    suspend fun resolveVanityUrl(
        @Query("key") key: String,
        @Query("vanityurl") vanityUrl: String,
        @Query("url_type") urlType: Int = 1
    ): Response<SteamId>
}