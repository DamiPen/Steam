package com.github.damipen.steam.steamnews

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class AppNewsResponse(@Json(name = "appnews") val appnews: AppNews)

@JsonClass(generateAdapter = true)
data class AppNews(
    @Json(name = "appid") val appId: Long,
    @Json(name = "count") val count: Int,
    @Json(name = "newsitems") val newsItems: List<News>
)

@JsonClass(generateAdapter = true)
data class News(
    @Json(name = "gid") val gid: String,
    @Json(name = "title") val title: String,
    @Json(name = "url") val url: String,
    @Json(name = "is_external_url") val isExternalUrl: Boolean,
    @Json(name = "author") val author: String,
    @Json(name = "contents") val contents: String,
    @Json(name = "feedlabel") val feedLabel: String,
    @Json(name = "date") val date: Long,
    @Json(name = "feedname") val feedName: String,
    @Json(name = "feed_type") val feedType: Int,
    @Json(name = "appid") val appId: Long
)