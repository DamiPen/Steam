package com.github.damipen.steam.user

import androidx.annotation.IntRange
import androidx.core.net.toUri
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
import kotlin.time.DurationUnit
import kotlin.time.toDuration

@JsonClass(generateAdapter = true)
data class SteamId(
    @Json(name = "success") val success: Int,
    @Json(name = "steamid") val steamId: String?,
    @Json(name = "message") val errorMessage: String?
) {
    val isSuccessful = success == 1
}

@JsonClass(generateAdapter = true)
data class Players<T>(
    @Json(name = "players") val playerList: List<T>
)

/**
 *
 * @param avatar 32*32px
 * @param avatarMedium 64*64px
 * @param avatarFull 184*184px
 */
@JsonClass(generateAdapter = true)
data class PlayerInfo(
    @Json(name = "steamid") val steamId: String,
    @Json(name = "communityvisibilitystate") val communityVisibilityState: Int,
    @Json(name = "profilestate") val profileState: Int,
    @Json(name = "personaname") val personaName: String,
    @Json(name = "profileurl") val profileUrl: String,
    @Json(name = "avatar") val avatar: String,
    @Json(name = "avatarmedium") val avatarMedium: String,
    @Json(name = "avatarfull") val avatarFull: String,
    @Json(name = "timecreated") val timeCreated: Long,
    @Json(name = "lastlogoff") val lastLogOff: Long?,
    @Json(name = "personastate") val personaState: Int,
    @Json(name = "realname") val realName: String?,
    @Json(name = "primaryclanid") val primaryClanId: String,
    @Json(name = "personastateflags") val personaStateFlags: Int,
    @Json(name = "loccountrycode") val locCountryCode: String?,
    @Json(name = "locstatecode") val locStateCode: String?,
    @Json(name = "loccityid") val locCityId: Int?
){
    fun creationDate() : String {
        return SimpleDateFormat("YYYY/MM/dd", Locale.FRANCE).format(Date(timeCreated.times(1000)))
    }
}

@JsonClass(generateAdapter = true)
data class PlayerBanInfo(
    @Json(name = "SteamId") val steamId: String,
    @Json(name = "CommunityBanned") val communityBanned: Boolean,
    @Json(name = "VACBanned") val vacBanned: Boolean,
    @Json(name = "NumberOfVACBans") val numberOfVACBans: Int,
    @Json(name = "DaysSinceLastBan") val daysSinceLastBan: Long,
    @Json(name = "NumberOfGameBans") val numberOfGameBans: Int,
    @Json(name = "EconomyBan") val economyBan: String
)

@JsonClass(generateAdapter = true)
data class FriendsList(@Json(name = "friendslist") val friendsList: Friends)

@JsonClass(generateAdapter = true)
data class Friends(@Json(name = "friends") val friendsList: List<Friend>)

@JsonClass(generateAdapter = true)
data class Friend(
    @Json(name = "steamid") val steamId: String,
    @Json(name = "relationship") val relationship: String,
    @Json(name = "friend_since") val friendSince: Long

)

@JsonClass(generateAdapter = true)
data class Groups(
    @Json(name = "success") val isSuccessful: Boolean,
    @Json(name = "groups") val groups: List<Group>
)

@JsonClass(generateAdapter = true)
data class Group(
    @Json(name = "gid") val id: String
)