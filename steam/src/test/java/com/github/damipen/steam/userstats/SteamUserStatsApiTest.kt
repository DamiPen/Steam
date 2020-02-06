package com.github.damipen.steam.userstats

import kotlinx.coroutines.runBlocking
import okhttp3.mockwebserver.MockWebServer
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNotNull
import org.junit.Assert.assertNull
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory

class SteamUserStatsApiTest {

    private lateinit var mockWebServer: MockWebServer
    private lateinit var api: SteamUserStatsApi
    private val steamKey = "key"
    private val steamId = "1"
    private val appId = 1L

    @Before
    fun setup() {
        mockWebServer = MockWebServer()
        mockWebServer.dispatcher = SteamUserStatsDispatcher
        mockWebServer.start()
        api = Retrofit.Builder()
            .baseUrl(mockWebServer.url("/"))
            .addConverterFactory(MoshiConverterFactory.create())
            .build()
            .create(SteamUserStatsApi::class.java)
    }

    @After
    fun tearDown() {
        mockWebServer.shutdown()
    }

    @Test
    fun getGlobalAchievementPercentagesForApp() = runBlocking {
        val globalAchievementPercentagesForApp = api.getGlobalAchievementPercentagesForApp(1L)
        assertNotNull(globalAchievementPercentagesForApp)
        val globalAchievementList = globalAchievementPercentagesForApp.achievements.globalAchievementList
        assertNotNull(globalAchievementList)
        assertEquals(1, globalAchievementList.count())

        val globalAchievement = globalAchievementList.first()
        globalAchievement.run {
            assertEquals("EP2_KILL_POISONANTLION", name)
            assertEquals(92.5, percent, 0.000001)
        }
    }

    @Test
    fun getNumberOfCurrentPlayers() = runBlocking {
        val numberOfCurrentPlayers = api.getNumberOfCurrentPlayers(appId)
        assertNotNull(numberOfCurrentPlayers)
        val response = numberOfCurrentPlayers.response
        assertNotNull(response)
        assertEquals(9360, response.count)
    }

    @Test
    fun getPlayerAchievements() = runBlocking {
        val playerAchievements = api.getPlayerAchievements(steamKey, steamId, appId)
        assertNotNull(playerAchievements)
        val playerStats = playerAchievements.playerStats
        assertNotNull(playerStats)
        playerStats.run {
            assertTrue(success!!)
            assertEquals("76561197964628349", steamId)
            assertEquals("Half-Life 2: Episode Two", gameName)
            assertEquals(1, achievements.count())
        }

        val achievements = playerStats.achievements.first()
        achievements.run {
            assertEquals(1, achieved)
            assertEquals("EP2_KILL_POISONANTLION", apiName)
            assertEquals(1234567, unlockTime)
        }
    }

    @Test
    fun getSchemaForGame() = runBlocking {
        val schemaForGame = api.getSchemaForGame(steamKey, appId)
        assertNotNull(schemaForGame)

        val game: Game.GameStat = schemaForGame.game
        assertNotNull(game)
        game.run {
            assertEquals("Blacksparrow", gameName)
            assertEquals("10", gameVersion)
        }

        val availableGameStats = game.availableGameStats
        assertNotNull(availableGameStats)
        val achievements = availableGameStats.achievements
        assertEquals(1, achievements.count())
        val achievement: Game.GameStat.GameStats.Achievement = achievements.first()
        achievement.run {
            assertEquals("ACH_1",name)
            assertEquals(0,defaultValue)
            assertEquals("Imperial Seal",displayName)
            assertEquals(1,hidden)
            assertEquals("https://steamcdn-a.akamaihd.net/steamcommunity/public/images/apps/403640/02620037f426b657c0a610c9482483af71bf1067.jpg",icon)
            assertEquals("https://steamcdn-a.akamaihd.net/steamcommunity/public/images/apps/403640/90fbf2c762cc6d39ad80ba64b1b065056da9dfc4.jpg", iconGray)
            assertNull(description)
        }

        assertNotNull(availableGameStats.stats)
        val stat: Game.GameStat.GameStats.Stats = availableGameStats.stats!!.first()
        stat.run {
            assertEquals("stat_0", name)
            assertEquals(0, defaultValue)
            assertEquals("KillEnnemy", displayName)
        }
    }

    @Test
    fun getUserStatsForGame() = runBlocking {
        val userStatsForGame = api.getUserStatsForGame(steamKey, steamId, appId)
        assertNotNull(userStatsForGame)
        userStatsForGame.playerStats.run {
            assertEquals("Half-Life 2: Episode Two", gameName)
            assertEquals("76561197964628349", steamId)
            assertNull(success)
        }

        val achievements = userStatsForGame.playerStats.achievements
        assertNotNull(achievements)
        assertEquals(1, achievements.count())

        val userAchievement = achievements.first()
        assertNotNull(userAchievement)
        userAchievement.run {
            assertEquals(1, achieved)
            assertEquals("EP2_KILL_POISONANTLION", name)
        }
    }

}