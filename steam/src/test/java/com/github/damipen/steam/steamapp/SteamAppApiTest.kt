package com.github.damipen.steam.steamapp

import kotlinx.coroutines.runBlocking
import okhttp3.mockwebserver.MockWebServer
import org.junit.After
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory

class SteamAppApiTest {

    private lateinit var mockWebServer: MockWebServer
    private lateinit var api: SteamAppApi

    @Before
    fun setup() {
        mockWebServer = MockWebServer()
        mockWebServer.dispatcher = SteamAppDispatcher
        mockWebServer.start()

        api = Retrofit.Builder()
            .baseUrl(mockWebServer.url("/"))
            .addConverterFactory(MoshiConverterFactory.create())
            .build()
            .create(SteamAppApi::class.java)
    }

    @After
    fun tearDown() {
        mockWebServer.shutdown()
    }

    @Test
    fun getAppList() = runBlocking {

        val appList = api.getAppList().apps.appsList

        Assert.assertEquals(1, mockWebServer.requestCount)

        Assert.assertEquals(1, appList.count())
        val app = appList.first()
        Assert.assertEquals(587650, app.appId)
        Assert.assertEquals("Half-Life 2: DownFall", app.name)
    }

}