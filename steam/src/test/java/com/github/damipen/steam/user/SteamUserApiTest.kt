package com.github.damipen.steam.user

import kotlinx.coroutines.runBlocking
import okhttp3.mockwebserver.MockWebServer
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Assert.assertFalse
import org.junit.Assert.assertNotNull
import org.junit.Assert.assertNull
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory

class SteamUserApiTest {

    private lateinit var mockWebServer: MockWebServer
    private lateinit var api: SteamUserApi
    private val SteamKey = "key"
    private val SteamId = "1"

    @Before
    fun setup() {
        mockWebServer = MockWebServer()
        mockWebServer.dispatcher = SteamUsersDispatcher
        mockWebServer.start()
        api = Retrofit.Builder()
            .baseUrl(mockWebServer.url("/"))
            .addConverterFactory(MoshiConverterFactory.create())
            .build()
            .create(SteamUserApi::class.java)
    }

    @After
    fun tearDown() {
        mockWebServer.shutdown()
    }

    @Test
    fun getFriendList() = runBlocking {
        val friendList = api.getFriendList(SteamKey, SteamId)

        assertNotNull(friendList)
        val friends = friendList.friendsList.friendsList
        assertEquals(1, friends.count())
        val friend = friends.first()
        assertEquals(friend.friendSince, 1456373433)
        assertEquals(friend.relationship, "friend")
        assertEquals(friend.steamId, "76561197946444150")
    }

    @Test
    fun `when steamgetFriendList`() = runBlocking {
        val friendList = api.getFriendList(SteamKey, SteamId)

        assertNotNull(friendList)
        val friends = friendList.friendsList.friendsList
        assertEquals(1, friends.count())
        val friend = friends.first()
        assertEquals(friend.friendSince, 1456373433)
        assertEquals(friend.relationship, "friend")
        assertEquals(friend.steamId, "76561197946444150")
    }

    @Test
    fun getPlayerBans() = runBlocking {
        val playerBans = api.getPlayerBans(SteamKey, SteamId)
        assertNotNull(playerBans)

        val playerList = playerBans.playerList
        assertEquals(1, playerList.count())

        val player = playerList.first()
        assertEquals(player.steamId, "76561197964650349")
        assertEquals(player.communityBanned, false)
        assertEquals(player.vacBanned, false)
        assertEquals(player.numberOfVACBans, 0)
        assertEquals(player.daysSinceLastBan, 0)
        assertEquals(player.numberOfGameBans, 0)
        assertEquals(player.economyBan, "none")
    }

    @Test
    fun getPlayerSummaries() = runBlocking {
        val playerSummaries = api.getPlayerSummaries(SteamKey, SteamId)

        assertNotNull(playerSummaries.response)
        assertEquals(1, playerSummaries.response.playerList.count())

        val summary = playerSummaries.response.playerList.first()
        assertEquals("76561197964628349", summary.steamId)
        assertEquals(3, summary.communityVisibilityState)
        assertEquals(1, summary.profileState)
        assertEquals("Xiaomi", summary.personaName)
        assertEquals("https://steamcommunity.com/id/xiaomi/", summary.profileUrl)
        assertEquals(
            "https://steamcdn-a.akamaihd.net/steamcommunity/public/images/avatars/96/9658ba67addb2c0f8b4da2bdac820d6e8e877112.jpg",
            summary.avatar
        )
        assertEquals(
            "https://steamcdn-a.akamaihd.net/steamcommunity/public/images/avatars/96/9658ba67addb2c0f8b4da2bdac820d6e8e877112_medium.jpg",
            summary.avatarMedium
        )
        assertEquals(
            "https://steamcdn-a.akamaihd.net/steamcommunity/public/images/avatars/96/9658ba67addb2c0f8b4da2bdac820d6e8e877112_full.jpg",
            summary.avatarFull
        )
        assertEquals(1077459014, summary.timeCreated)
        assertEquals(1581352638L, summary.lastLogOff)
        assertEquals(3, summary.personaState)
        assertEquals("damien", summary.realName)
        assertEquals("103582791429521408", summary.primaryClanId)
        assertEquals(0, summary.personaStateFlags)
        assertEquals("JP", summary.locCountryCode)
        assertEquals("40", summary.locStateCode)
        assertEquals(26138, summary.locCityId)

    }

    @Test
    fun getUserGroupList() = runBlocking {
        val userGroupList = api.getUserGroupList(SteamKey, SteamId)
        assertNotNull(userGroupList)

        val groupsresponse = userGroupList.response
        assertTrue(groupsresponse.isSuccessful)

        val group = groupsresponse.groups.first()
        assertEquals("118395", group.id)
    }

    @Test
    fun resolveVanityUrl() = runBlocking {
        val resolveVanityUrl = api.resolveVanityUrl(SteamKey, "xiaomi")
        assertNotNull(resolveVanityUrl.response)

        val response = resolveVanityUrl.response
        assertEquals(1, response.success)
        assertTrue(response.isSuccessful)
        assertEquals(response.steamId, "76561197964628349")
        assertNull(response.errorMessage)
    }

    @Test
    fun `error resolveVanityUrl`() = runBlocking {
        val resolveVanityUrl = api.resolveVanityUrl(SteamKey, "xiao")
        assertNotNull(resolveVanityUrl.response)

        val response = resolveVanityUrl.response
        assertNull(response.steamId)
        assertEquals(42, response.success)
        assertFalse(response.isSuccessful)
        assertEquals(response.errorMessage, "No match")
    }

    @Test(expected = retrofit2.HttpException::class)
    fun `given no key when getFriendList then 400`() = runBlocking {
        api.getFriendList("", SteamId)
        Unit
    }

    @Test(expected = retrofit2.HttpException::class)
    fun `given no steamid when getFriendList then 400`() = runBlocking {
        api.getFriendList(SteamKey, "")
        Unit
    }

    @Test(expected = retrofit2.HttpException::class)
    fun `given no key when getPlayerBans then 400`() = runBlocking {
        api.getPlayerBans("", SteamId)
        Unit
    }

    @Test(expected = retrofit2.HttpException::class)
    fun `given no steamid when getPlayerBans then 400`() = runBlocking {
        api.getPlayerBans(SteamKey, "")
        Unit
    }

    @Test(expected = retrofit2.HttpException::class)
    fun `given no key when getPlayerSummaries then 400`() = runBlocking {
        api.getPlayerSummaries("", SteamId)
        Unit
    }

    @Test(expected = retrofit2.HttpException::class)
    fun `given no steamid when getPlayerSummaries then 400`() = runBlocking {
        api.getPlayerSummaries(SteamKey, "")
        Unit
    }

    @Test(expected = retrofit2.HttpException::class)
    fun `given no key when getUserGroupList then 400`() = runBlocking {
        api.getUserGroupList("", SteamId)
        Unit
    }

    @Test(expected = retrofit2.HttpException::class)
    fun `given no steamid when getUserGroupList then 400`() = runBlocking {
        api.getUserGroupList(SteamKey, "")
        Unit
    }

    @Test(expected = retrofit2.HttpException::class)
    fun `given no key when resolveVanityUrl then 400`() = runBlocking {
        api.resolveVanityUrl("", "xiaomi")
        Unit
    }

}