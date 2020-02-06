package com.github.damipen.steam

import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import java.net.HttpURLConnection

fun <T : Any> T.readFile(path: String): String {
    return runCatching { javaClass.classLoader!!.getResourceAsStream(path).reader().useLines { it.joinToString("") } }
        .getOrElse { throw Throwable("Error for path $path", it) }
}

val badRequest = MockResponse().setResponseCode(HttpURLConnection.HTTP_BAD_REQUEST)
val unauthorized = MockResponse().setResponseCode(HttpURLConnection.HTTP_UNAUTHORIZED)
val forbidden = MockResponse().setResponseCode(HttpURLConnection.HTTP_FORBIDDEN)
val internalError = MockResponse().setResponseCode(HttpURLConnection.HTTP_INTERNAL_ERROR)

fun MockResponse.fromPath(path: String): MockResponse {
    setResponseCode(HttpURLConnection.HTTP_OK)
    setBody(readFile(path))
    return this
}