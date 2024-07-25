import io.ktor.http.*
import io.ktor.http.cio.*
import io.ktor.server.testing.*
import io.mockk.*
import junit.framework.TestCase.assertEquals
import kotlinx.coroutines.runBlocking
import net.grandcentrix.backend.TestDatabaseSetup.startDatabase
import net.grandcentrix.backend.dao.daoapi
import net.grandcentrix.backend.models.Character
import net.grandcentrix.backend.plugins.api.APIRequesting
import net.grandcentrix.backend.plugins.api.APIRequesting.daoApi
import org.junit.Assert.assertNotEquals
import org.junit.Test
import kotlin.test.BeforeTest


class APITest {
    private val API_URL = "https://api.potterdb.com/v1"
    @BeforeTest
    fun beforeTest() {
        startDatabase()
    }

    @Test
    fun testFetchCharacterEmpty() = testApplication {
        // non-matching existing credentials
        val cachedCharacters = daoapi.getCharactersByPage(7, 100)
        assertEquals(0, cachedCharacters.characters.size) // assuming no characters are cached initially
    }

    @Test
    fun testFetchCharacterDataDeletion() = testApplication {
      //  val clientMock = mockk<Client>()
        mockkObject(APIRequesting.client)
        coEvery { APIRequesting.client.get("$API_URL/characters?page[number]=1") } returns mockk()


        val characters = APIRequesting.fetchCharacters(1)

        daoApi.saveCharacters(characters)
        assertEquals(0, daoapi.getTotalCharacterCount())
    }
}



