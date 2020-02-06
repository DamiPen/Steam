package com.github.damipen.steam

import com.github.damipen.steam.playerservice.PlayerServiceApi
import com.github.damipen.steam.steamapp.SteamAppApi
import com.github.damipen.steam.steamnews.SteamNewsApi
import com.github.damipen.steam.store.StoreServiceApi
import com.github.damipen.steam.store.StoreServiceUsecase
import com.github.damipen.steam.user.Friend
import com.github.damipen.steam.user.Group
import com.github.damipen.steam.user.PlayerBanInfo
import com.github.damipen.steam.user.PlayerInfo
import com.github.damipen.steam.user.SteamId
import com.github.damipen.steam.user.SteamUserApi
import com.github.damipen.steam.userstats.SteamUserStatsApi
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory

class Steam(private val key: String) {

    private val retrofit: Retrofit = Retrofit.Builder()
        .client(
            OkHttpClient()
                .newBuilder()
                .addInterceptor(HttpLoggingInterceptor().apply { level = HttpLoggingInterceptor.Level.BODY })
                .build()
        )
        .addConverterFactory(MoshiConverterFactory.create())
        .baseUrl("https://api.steampowered.com/")
        .build()
    private val playerServiceApi: PlayerServiceApi = retrofit.create(PlayerServiceApi::class.java)
    private val steamAppApi: SteamAppApi = retrofit.create(SteamAppApi::class.java)
    private val steamNewsApi: SteamNewsApi = retrofit.create(SteamNewsApi::class.java)
    private val storeService: StoreServiceApi = retrofit.create(StoreServiceApi::class.java)
    private val steamUserApi: SteamUserApi = retrofit.create(SteamUserApi::class.java)
    private val steamUserStatsApi: SteamUserStatsApi = retrofit.create(SteamUserStatsApi::class.java)

    val storeServiceUsecase: StoreServiceUsecase = StoreServiceUsecase(key, storeService)


    suspend fun resolveVanityUrl(): SteamId {
        return steamUserApi.resolveVanityUrl(key, "xiaomi").response
    }

    suspend fun getFriendList(steamId: String): List<Friend> {
        return steamUserApi.getFriendList(key, steamId).friendsList.friendsList
    }

    suspend fun getPlayerBans(steamIds: String): List<PlayerBanInfo> {
        return steamUserApi.getPlayerBans(key, steamIds).playerList
    }

    suspend fun getPlayerSummaries(steamIds: String): List<PlayerInfo> {
        return steamUserApi.getPlayerSummaries(key, steamIds).response.playerList
    }

    suspend fun getUserGroupList(steamId: String): List<Group> {
        return steamUserApi.getUserGroupList(key, steamId).response.groups
    }

    suspend fun getFriendsInfo(steamId: String): List<PlayerInfo> {
        return steamUserApi.getFriendList(key, steamId).friendsList.friendsList
            .map { it.steamId }
            .joinToString(separator = ",")
            .run { steamUserApi.getPlayerSummaries(key, this) }
            .response.playerList
    }

}