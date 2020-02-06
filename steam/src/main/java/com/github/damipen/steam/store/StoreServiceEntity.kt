package com.github.damipen.steam.store

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class Apps(
    @Json(name = "apps") val appList: List<App>,
    @Json(name = "have_more_results") val haveMoreResults: Boolean,
    @Json(name = "last_appid") val lastAppid: Long
) {
    @JsonClass(generateAdapter = true)
    data class App(
        @Json(name = "appid") val id: Long,
        @Json(name = "name") val name: String,
        @Json(name = "last_modified") val lastModified: Long,
        @Json(name = "price_change_number") val priceChangeNumber: Long
    )
}

@Entity
data class AppRoom(
    @PrimaryKey val appid: Long,
    @ColumnInfo(name = "name") val name: String,
    @ColumnInfo(name = "last_modified") val lastModified: Long,
    @ColumnInfo(name = "price_change_number") val priceChangeNumber: Long,
    @ColumnInfo(name = "type") val type: Int
)