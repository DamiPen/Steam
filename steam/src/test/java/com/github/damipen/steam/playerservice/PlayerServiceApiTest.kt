package com.github.damipen.steam.playerservice

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
import java.net.HttpURLConnection

class PlayerServiceApiTest {

    private lateinit var mockWebServer: MockWebServer
    private lateinit var api: PlayerServiceApi
    private val steamKey = "key"
    private val steamId = "1"

    @Before
    fun setup() {
        mockWebServer = MockWebServer()
        mockWebServer.dispatcher = PlayerServiceDispatcher
        mockWebServer.start()

        api = Retrofit.Builder()
            .baseUrl(mockWebServer.url("/"))
            .addConverterFactory(MoshiConverterFactory.create())
            .build()
            .create(PlayerServiceApi::class.java)
    }

    @After
    fun tearDown() {
        mockWebServer.shutdown()
    }

    @Test
    fun getRecentlyPlayedGames() = runBlocking {
        val recentlyPlayedGames = api.getRecentlyPlayedGames(steamKey, steamId)
        assertNotNull(recentlyPlayedGames)

        val response = recentlyPlayedGames.response
        assertNotNull(response)
        assertEquals(1, response.totalCount)
        assertEquals(1, response.gameList.count())

        val game: RecentGame.Game = response.gameList.first()
        game.run {
            assertEquals(212680, appId)
            assertEquals("FTL: Faster Than Light", name)
            assertEquals(385, playtime2weeks)
            assertEquals(6937, playtimeForever)
            assertEquals("e8770ddb95fde0804694b116dfe3479f5a65900d", imgIconUrl)
            assertEquals("975193db5ca8cc2a4c969cea8f80d93157264ec1", imgLogoUrl)
            assertEquals(385, playtimeWindowsForever)
            assertEquals(122, playtimeMacForever)
            assertEquals(0, playtimeLinuxForever)
        }
    }

    @Test
    fun `given no key when getRecentlyPlayedGames then 403`() = runBlocking {
        val result = runCatching { api.getRecentlyPlayedGames("", steamId) }
        assertTrue(result.isFailure)

        val t = result.exceptionOrNull() as HttpException
        assertEquals(HttpURLConnection.HTTP_FORBIDDEN, t.code())
    }

    @Test
    fun `given no key when getRecentlyPlayedGames then 500`() = runBlocking {
        val result = runCatching { api.getRecentlyPlayedGames(steamKey, "") }
        assertTrue(result.isFailure)

        val t = result.exceptionOrNull() as HttpException
        assertEquals(HttpURLConnection.HTTP_INTERNAL_ERROR, t.code())
    }

    @Test
    fun getOwnedGames() = runBlocking {
        val ownedGames = api.getOwnedGames(steamKey, steamId)
        assertNotNull(ownedGames)

        val response = ownedGames.response
        assertNotNull(response)
        assertEquals(1, response.gameCount)
        assertEquals(1, response.game.count())

        val game: OwnedGames.Game = response.game.first()
        game.run {
            assertEquals(10, appId)
            assertEquals(1, playtimeForever)
            assertEquals(2, playtimeWindowsForever)
            assertEquals(3, playtimeMacForever)
            assertEquals(4, playtimeLinuxForever)
        }
    }

    @Test
    fun `given no key when getOwnedGames then 401`() = runBlocking {
        val result = runCatching { api.getOwnedGames("", steamId) }
        assertTrue(result.isFailure)

        val t = result.exceptionOrNull() as HttpException
        assertEquals(HttpURLConnection.HTTP_UNAUTHORIZED, t.code())
    }

    @Test
    fun `given no key when getOwnedGames then 500`() = runBlocking {
        val result = runCatching { api.getOwnedGames(steamKey, "") }
        assertTrue(result.isFailure)

        val t = result.exceptionOrNull() as HttpException
        assertEquals(HttpURLConnection.HTTP_INTERNAL_ERROR, t.code())
    }

    @Test
    fun getSteamLevel() = runBlocking {
        val steamLevel = api.getSteamLevel(steamKey, steamId)
        assertNotNull(steamLevel)

        val response = steamLevel.response
        assertNotNull(response)
        assertEquals(60, response.playerLevel)
    }

    @Test
    fun `given no key when getSteamLevel then 403`() = runBlocking {
        val result = runCatching { api.getSteamLevel("", steamId) }
        assertTrue(result.isFailure)

        val t = result.exceptionOrNull() as HttpException
        assertEquals(HttpURLConnection.HTTP_FORBIDDEN, t.code())
    }

    @Test
    fun `given no key when getSteamLevel then 500`() = runBlocking {
        val result = runCatching { api.getSteamLevel(steamKey, "") }
        assertTrue(result.isFailure)

        val t = result.exceptionOrNull() as HttpException
        assertEquals(HttpURLConnection.HTTP_INTERNAL_ERROR, t.code())
    }

    @Test
    fun getBadges() = runBlocking {
        val getBadges = api.getBadges(steamKey, steamId)
        assertNotNull(getBadges)

        val response = getBadges.response
        assertNotNull(response)
        response.run {
            assertEquals(1, badges.count())
            assertEquals(21483, playerXp)
            assertEquals(60, playerLevel)
            assertEquals(217, playerXpNeededToLevelUp)
            assertEquals(21000, playerXpNeeded_CurrentLevel)
        }

        val badges: BadgeInfo.Badge = response.badges.first()
        badges.run {
            assertEquals(44, badgeid)
            assertEquals(1, level)
            assertEquals(1577290404, completionTime)
            assertEquals(250, xp)
            assertEquals(1925000, scarcity)
        }
    }

    @Test
    fun `given no key when getBadges then 403`() = runBlocking {
        val result = runCatching { api.getBadges("", steamId) }
        assertTrue(result.isFailure)

        val t = result.exceptionOrNull() as HttpException
        assertEquals(HttpURLConnection.HTTP_FORBIDDEN, t.code())
    }

    @Test
    fun `given no key when getBadges then 500`() = runBlocking {
        val result = runCatching { api.getBadges(steamKey, "") }
        assertTrue(result.isFailure)

        val t = result.exceptionOrNull() as HttpException
        assertEquals(HttpURLConnection.HTTP_INTERNAL_ERROR, t.code())
    }

    @Test
    fun getCommunityBadgeProgress() = runBlocking {
        val communityBadgeProgress = api.getCommunityBadgeProgress(steamKey, steamId)
        assertNotNull(communityBadgeProgress)

        val response = communityBadgeProgress.response
        assertNotNull(response)

        val quests = response.quests
        assertNotNull(quests)
        assertEquals(1, quests.count())

        val quest = quests.first()
        quest.run {
            assertEquals(115, questId)
            assertTrue(completed)
        }
    }

    @Test
    fun `given no key when getCommunityBadgeProgress then 403`() = runBlocking {
        val result = runCatching { api.getCommunityBadgeProgress("", steamId) }
        assertTrue(result.isFailure)

        val t = result.exceptionOrNull() as HttpException
        assertEquals(HttpURLConnection.HTTP_FORBIDDEN, t.code())
    }

    @Test
    fun `given no key when getCommunityBadgeProgress then 500`() = runBlocking {
        val result = runCatching { api.getCommunityBadgeProgress(steamKey, "") }
        assertTrue(result.isFailure)

        val t = result.exceptionOrNull() as HttpException
        assertEquals(HttpURLConnection.HTTP_INTERNAL_ERROR, t.code())
    }

    @Test
    fun isPlayingSharedGame() = runBlocking {
        val playingSharedGame = api.isPlayingSharedGame(steamKey, steamId)
        assertNotNull(playingSharedGame)

        val response = playingSharedGame.response
        assertNotNull(response)

        assertEquals("0", response.lenderSteamid)
    }

    @Test
    fun `given no key when isPlayingSharedGame then 403`() = runBlocking {
        val result = runCatching { api.isPlayingSharedGame("", steamId) }
        assertTrue(result.isFailure)

        val t = result.exceptionOrNull() as HttpException
        assertEquals(HttpURLConnection.HTTP_FORBIDDEN, t.code())
    }

    @Test
    fun `given no key when isPlayingSharedGame then 500`() = runBlocking {
        val result = runCatching { api.isPlayingSharedGame(steamKey, "") }
        assertTrue(result.isFailure)

        val t = result.exceptionOrNull() as HttpException
        assertEquals(HttpURLConnection.HTTP_INTERNAL_ERROR, t.code())
    }

}