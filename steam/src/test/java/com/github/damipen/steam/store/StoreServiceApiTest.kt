import com.github.damipen.steam.store.StoreServiceApi
import com.github.damipen.steam.store.StoreServiceDispatcher
import kotlinx.coroutines.runBlocking
import okhttp3.mockwebserver.MockWebServer
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNotNull
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory

class StoreServiceApiTest {

    private lateinit var mockWebServer: MockWebServer
    private lateinit var api: StoreServiceApi

    @Before
    fun setup() {
        mockWebServer = MockWebServer()
        mockWebServer.dispatcher = StoreServiceDispatcher
        mockWebServer.start()
        api = Retrofit.Builder()
            .baseUrl(mockWebServer.url("/"))
            .addConverterFactory(MoshiConverterFactory.create())
            .build()
            .create(StoreServiceApi::class.java)
    }

    @After
    fun tearDown() {
        mockWebServer.shutdown()
    }

    @Test
    fun getAppList() = runBlocking {

        val appList = api.getAppList("key")
        assertNotNull(appList.response)

        val resp = appList.response
        assertTrue(resp.haveMoreResults)
        assertEquals(20, resp.lastAppid)

        val apps = appList.response.appList
        assertEquals(1, apps.count())

        val app = apps.first()
        assertEquals(10, app.id)
        assertEquals(1568751918, app.lastModified)
        assertEquals("Counter-Strike", app.name)
        assertEquals(7469765, app.priceChangeNumber)
    }

}
