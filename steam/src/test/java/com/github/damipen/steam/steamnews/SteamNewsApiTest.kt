package com.github.damipen.steam.steamnews

import kotlinx.coroutines.runBlocking
import okhttp3.mockwebserver.MockWebServer
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNotNull
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test
import retrofit2.HttpException
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory

class SteamNewsApiTest {

    private lateinit var mockWebServer: MockWebServer
    private lateinit var api: SteamNewsApi

    @Before
    fun setup() {
        mockWebServer = MockWebServer()
        mockWebServer.dispatcher = SteamNewsDispatcher
        mockWebServer.start()

        api = Retrofit.Builder()
            .baseUrl(mockWebServer.url("/"))
            .addConverterFactory(MoshiConverterFactory.create())
            .build()
            .create(SteamNewsApi::class.java)
    }

    @After
    fun tearDown() {
        mockWebServer.shutdown()
    }

    @Test
    fun getNewsForApp() = runBlocking {
        val newsForApp = api.getNewsForApp("420")
        assertNotNull(newsForApp)

        val appnews = newsForApp.appnews
        assertEquals(420, appnews.appId)
        assertEquals(1, appnews.count)
        assertEquals(1, appnews.newsItems.count())

        val news: News = appnews.newsItems.first()
        news.run {
            assertEquals("2597949245043331091", gid)
            assertEquals("The whole Half-Life story is free to play on Steam right now", title)
            assertEquals("https://steamstore-a.akamaihd.net/news/externalpost/rps/2597949245043331091", url)
            assertEquals(true, isExternalUrl)
            assertEquals("contact@rockpapershotgun.com", author)
            assertEquals("contents", contents)
            assertEquals("Rock, Paper, Shotgun", feedLabel)
            assertEquals(1579692666, date)
            assertEquals("rps", feedName)
            assertEquals(0, feedType)
            assertEquals(70, appId)
        }
    }

    @Test
    fun `given no appid when getNewsForApp then 400`() = runBlocking {
        val result = kotlin.runCatching { api.getNewsForApp("") }
        assertTrue(result.isFailure)

        val t = result.exceptionOrNull() as HttpException
        assertEquals(400, t.code())
    }
}