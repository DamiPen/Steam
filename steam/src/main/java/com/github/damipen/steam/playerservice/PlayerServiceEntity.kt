package com.github.damipen.steam.playerservice

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class RecentGame(
    @Json(name = "total_count") val totalCount: Int,
    @Json(name = "games") val gameList: List<Game>
) {
    @JsonClass(generateAdapter = true)
    data class Game(
        @Json(name = "appid") val appId: Long,
        @Json(name = "name") val name: String,
        @Json(name = "playtime_2weeks") val playtime2weeks: Long,
        @Json(name = "playtime_forever") val playtimeForever: Long,
        @Json(name = "img_icon_url") val imgIconUrl: String,
        @Json(name = "img_logo_url") val imgLogoUrl: String,
        @Json(name = "playtime_windows_forever") val playtimeWindowsForever: Long,
        @Json(name = "playtime_mac_forever") val playtimeMacForever: Long,
        @Json(name = "playtime_linux_forever") val playtimeLinuxForever: Long
    )
}

@JsonClass(generateAdapter = true)
data class OwnedGames(
    @Json(name = "game_count") val gameCount: Int,
    @Json(name = "games") val game: List<Game>
) {
    @JsonClass(generateAdapter = true)
    data class Game(
        @Json(name = "appid") val appId: Long,
        @Json(name = "playtime_forever") val playtimeForever: Long,
        @Json(name = "playtime_windows_forever") val playtimeWindowsForever: Long,
        @Json(name = "playtime_mac_forever") val playtimeMacForever: Long,
        @Json(name = "playtime_linux_forever") val playtimeLinuxForever: Long
    )
}

@JsonClass(generateAdapter = true)
data class PlayerLevel(
    @Json(name = "player_level") val playerLevel: Int
)

@JsonClass(generateAdapter = true)
data class BadgeInfo(
    @Json(name = "badges") val badges: List<Badge>,
    @Json(name = "player_xp") val playerXp: Long,
    @Json(name = "player_level") val playerLevel: Long,
    @Json(name = "player_xp_needed_to_level_up") val playerXpNeededToLevelUp: Long,
    @Json(name = "player_xp_needed_current_level") val playerXpNeeded_CurrentLevel: Long
) {
    @JsonClass(generateAdapter = true)
    data class Badge(
        @Json(name = "badgeid") val badgeid: Long,
        @Json(name = "level") val level: Long,
        @Json(name = "completion_time") val completionTime: Long,
        @Json(name = "xp") val xp: Long,
        @Json(name = "scarcity") val scarcity: Long
    )
}

@JsonClass(generateAdapter = true)
data class CommunityBadgeProgress(
    @Json(name = "quests") val quests: List<Quest>
) {
    @JsonClass(generateAdapter = true)
    data class Quest(
        @Json(name = "questid") val questId: Long,
        @Json(name = "completed") val completed: Boolean
    )
}

@JsonClass(generateAdapter = true)
data class Lender(@Json(name = "lender_steamid") val lenderSteamid: String)

