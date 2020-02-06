package com.github.damipen.steam

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class Response<T>(@Json(name="response") val response: T)
