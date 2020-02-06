package com.github.damipen.steam.userstats

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass
import org.json.JSONObject

@JsonClass(generateAdapter = true)
data class AchievementPercentages(@Json(name = "achievementpercentages") val achievements: Achievements)

@JsonClass(generateAdapter = true)
data class Achievements(@Json(name = "achievements") val globalAchievementList: List<GlobalAchievement>)

@JsonClass(generateAdapter = true)
data class GlobalAchievement(
    @Json(name = "name") val name: String,
    @Json(name = "percent") val percent: Double
)

@JsonClass(generateAdapter = true)
data class PlayerCount(@Json(name = "player_count") val count: Long)

@JsonClass(generateAdapter = true)
data class PlayerStats<T>(@Json(name = "playerstats") val playerStats: PlayerGame<T>) {

    @JsonClass(generateAdapter = true)
    data class PlayerGame<T>(
        @Json(name = "steamID") val steamId: String,
        @Json(name = "gameName") val gameName: String,
        @Json(name = "achievements") val achievements: List<T>,
        @Json(name = "success") val success: Boolean?
    ) {
        @JsonClass(generateAdapter = true)
        data class PlayerAchievement(
            @Json(name = "apiname") val apiName: String,
            @Json(name = "achieved") val achieved: Int,
            @Json(name = "unlocktime") val unlockTime: Long
        )

        @JsonClass(generateAdapter = true)
        data class UserAchievement(
            @Json(name = "name") val name: String,
            @Json(name = "achieved") val achieved: Int
        )
    }
}

@JsonClass(generateAdapter = true)
data class Game(@Json(name = "game") val game: GameStat) {

    @JsonClass(generateAdapter = true)
    data class GameStat(
        @Json(name = "gameName") val gameName: String,
        @Json(name = "gameVersion") val gameVersion: String,
        @Json(name = "availableGameStats") val availableGameStats: GameStats
    ) {
        @JsonClass(generateAdapter = true)
        data class GameStats(
            @Json(name = "achievements") val achievements: List<Achievement>,
            @Json(name = "stats") val stats: List<Stats>?
        ) {
            @JsonClass(generateAdapter = true)
            data class Achievement(
                @Json(name = "name") val name: String,
                @Json(name = "defaultvalue") val defaultValue: Int,
                @Json(name = "displayName") val displayName: String,
                @Json(name = "hidden") val hidden: Int,
                @Json(name = "description") val description: String?,
                @Json(name = "icon") val icon: String,
                @Json(name = "icongray") val iconGray: String
            )

            @JsonClass(generateAdapter = true)
            data class Stats(
                @Json(name = "name") val name: String,
                @Json(name = "defaultvalue") val defaultValue: Int,
                @Json(name = "displayName") val displayName: String
            )
        }
    }
}