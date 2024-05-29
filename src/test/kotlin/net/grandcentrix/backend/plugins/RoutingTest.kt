package net.grandcentrix.backend.plugins

import io.ktor.client.*
import io.ktor.client.engine.cio.*
import io.ktor.client.plugins.cookies.*
import io.ktor.client.request.*
import io.ktor.client.statement.*
import io.ktor.http.*
import io.ktor.server.config.*
import io.ktor.server.testing.*
import io.mockk.*
import kotlinx.coroutines.runBlocking
import net.grandcentrix.backend.controllers.Signup.Companion.SignupInstance
import net.grandcentrix.backend.dao.daoUsers
import net.grandcentrix.backend.models.Book
import net.grandcentrix.backend.repository.BooksRepository.Companion.BooksRepositoryInstance
import org.jetbrains.exposed.sql.Database
import org.junit.Test
import kotlin.test.*


class RoutingTest {

    companion object {
        private val config = ApplicationConfig("./src/main/resources/application.conf")
        val driverClassName = config.property("storage.driverClassName").getString()
        val url = config.property("storage.jdbcURL").getString()
    }

    @BeforeTest
    fun beforeTest() {
        val books = listOf<Book>()

        mockkObject(BooksRepositoryInstance)
        every { BooksRepositoryInstance.getAll() } returns books

    }

    @AfterTest
    fun afterTest() {
        unmockkAll()
    }

    @Test
    fun testHomepage() = testApplication {
        val response = client.get("/")

        assertEquals(HttpStatusCode.OK, response.status)
    }

    @Test
    fun getLoginPage() = testApplication {
        // Sending a GET request to "/login" endpoint
        val response = client.get("/login")
        // Asserting that the response status code is HttpStatusCode.OK (200)
        assertEquals(HttpStatusCode.OK, response.status)
    }

    @Test
    fun testLoginWithValidCredentials() = testApplication {
        // Sending a POST request to "/login" endpoint with valid username and password
        val response = client.post("/login") {
            // Setting the content type header to application/x-www-form-urlencoded
            header(HttpHeaders.ContentType, ContentType.Application.FormUrlEncoded.toString())
            // Setting the request body with username and password encoded in form-url-encoded format
            setBody(listOf("username" to "123", "password" to "123").formUrlEncode())
        }
        // Asserting that the response status code is HttpStatusCode.Found (302)
        assertEquals(HttpStatusCode.Found, response.status)
    }

    @Test
    fun testLoginWithInvalidCredentials() = testApplication {
        // Sending a POST request to "/login" endpoint with invalid username and password
        val response = client.post("/login") {
            // Setting the content type header to application/x-www-form-urlencoded
            header(HttpHeaders.ContentType, ContentType.Application.FormUrlEncoded.toString())
            // Setting the request body with invalid username and password encoded in form-url-encoded format
            setBody(listOf("username" to "invaliduser", "password" to "pass").formUrlEncode())
        }
        // Extracting the 'Location' header from the response, which contains the redirected URL
        val location = response.headers["Location"].toString()
        // Sending a GET request to the URL obtained from the 'Location' header
        val redirectedResponse = client.get(location).bodyAsText()
        // Asserting that the redirected response body contains the expected message "Login not authorized"
        assertContains(redirectedResponse, "Login is invalid!")
        // Asserting that the response status code is HttpStatusCode.Found (302)
        assertEquals(HttpStatusCode.Found, response.status)
    }

    @Test
    fun getSignUpPage() = testApplication {
        // Sending a GET request to "/signup" endpoint
        val response = client.get("/signup")
        // Asserting that the response status code is HttpStatusCode.OK (200)
        assertEquals(HttpStatusCode.OK, response.status)
    }

    @Test
    fun createAccountWithSuccess() = testApplication {
        // Simulating form parameters for signup
        val formParameters = Parameters.build {
            append("name", "John")
            append("surname", "Doe")
            append("email", "john.doe@example.com")
            append("username", "testuser")
            append("password", "testpassword")
        }

        mockkObject(SignupInstance)
        every { SignupInstance.createUser(formParameters) } just Runs

        // Sending a POST request to "/signup" endpoint with form parameters
        val signupResponse = client.post("/signup") {
            // Setting the content type header to application
            header(HttpHeaders.ContentType, ContentType.Application.FormUrlEncoded.toString())
            // Setting the request body with form parameters encoded in form-url-encoded format
            setBody(formParameters.formUrlEncode())
        }

        val location =  signupResponse.headers["Location"].toString()

        // Asserting that the signup response status code is HttpStatusCode.Found (302)
        // as it should redirect to "/login"
        assertEquals(HttpStatusCode.Found, signupResponse.status)

        // Asserting that the Location header redirects to "/login"
        assertEquals("/login", location)
    }

    @Test
    fun createAccountWithInvalidSignup() = testApplication {
        // Simulating form parameters for signup with invalid data
        val formParameters = Parameters.build {
            // Appending all parameters with one being empty
            append("name", "")
            append("surname", "Doe")
            append("email", "testemail@email.com")
            append("username", "testuser")
            append("password", "testpassword")
        }

        mockkObject(SignupInstance)
        every { SignupInstance.createUser(formParameters) } just Runs

        // Sending a POST request to "/signup" endpoint with form parameters
        val response = client.post("/signup") {
            // Setting the content type header to application
            header(HttpHeaders.ContentType, ContentType.Application.FormUrlEncoded.toString())
            // Setting the request body with form parameters encoded in form-url-encoded format
            setBody(formParameters.formUrlEncode())
        }

        assertEquals(HttpStatusCode.Found, response.status)
    }

    @Test
    fun accessProfilePageNotAuthenticated() = testApplication {

        val response = client.get("/profile") {
            // No session cookie added
        }

        // Assert that the response status code is HttpStatusCode.Found (302),
        // indicating a redirection to another page
        assertEquals(HttpStatusCode.OK, response.status)

        // Assert that the URL of the request is "/login", indicating redirection
        val url = response.request.url.encodedPath
        assertEquals("/login", url)
    }

    @Test
    fun accessProfilePageAuthenticated() = testApplication {

        // Retrieve username from storage for test purpose
        Database.connect(url, driverClassName)
        val username = daoUsers.getAll().firstOrNull()?.username ?: ""

        // Send a GET request to "/profile" endpoint with authenticated session
        val response = client.get("/profile") {
            // Add a session cookie to the request header
            cookie("auth-session", username)
        }

        // Assert that the response status code is HttpStatusCode.OK (200)
        assertEquals(HttpStatusCode.OK, response.status)

        // Assert that the response location header is null
        assertNull(response.headers["Location"])
    }

    @Test
    fun getLogoutPage() = testApplication {

        val client = HttpClient(CIO) {
            install(HttpCookies) {
                storage = ConstantCookiesStorage(Cookie(name = "username", value = "test", domain = "0.0.0.0"))
            }
        }

        val response = runBlocking { client.get("/logout") }
        val cookies = response.call.client.cookies("/")

        // check if there's no session
        //TODO

        assertEquals(HttpStatusCode.OK, response.status)
        assertEquals("/", response.headers["Location"])

    }

    @Test
    fun testGetBooks() = testApplication {
        val response = client.get("/books")

        assertEquals(HttpStatusCode.OK, response.status)
        assertContains(response.bodyAsText(), "Books")
    }

    @Test
    fun testGetCharacters() = testApplication {
        val response = client.get("/characters")

        assertEquals(HttpStatusCode.OK, response.status)
        assertContains(response.bodyAsText(), "Characters")
    }

    @Test
    fun testGetHouses() = testApplication {
        val response = client.get("/houses")

        assertEquals(HttpStatusCode.OK, response.status)
        assertContains(response.bodyAsText(), "Houses")
    }

    @Test
    fun testGetMovies() = testApplication {
        val response = client.get("/movies")

        assertEquals(HttpStatusCode.OK, response.status)
        assertContains(response.bodyAsText(), "Movies")
    }

    @Test
    fun testGetPotions() = testApplication {
        val response = client.get("/potions")

        assertEquals(HttpStatusCode.OK, response.status)
        assertContains(response.bodyAsText(), "Potions")
    }

    @Test
    fun testGetSpells() = testApplication {
        val response = client.get("/spells")

        assertEquals(HttpStatusCode.OK, response.status)
        assertContains(response.bodyAsText(), "Spells")
    }

    //TODO: Delete account route test
}