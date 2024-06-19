package net.grandcentrix.backend

import io.ktor.client.request.*
import io.ktor.client.request.forms.*
import io.ktor.client.statement.*
import io.ktor.http.*
import io.ktor.server.config.*
import io.ktor.server.testing.*
import io.mockk.every
import io.mockk.mockkObject
import io.mockk.unmockkAll
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.encodeToJsonElement
import net.grandcentrix.backend.controllers.UserSession
import net.grandcentrix.backend.dao.DatabaseSingleton
import net.grandcentrix.backend.dao.daoUsers
import net.grandcentrix.backend.models.User
import org.jetbrains.exposed.sql.Database
import org.junit.BeforeClass
import org.junit.AfterClass
import org.junit.Test
import java.io.File
import kotlin.test.*

class RoutingTest {

    companion object {
        private const val FILE_NAME = "src/main/resources/testFile.json"
        private lateinit var database: Database

        @BeforeClass
        @JvmStatic
        fun beforeTest() {
            val config = ApplicationConfig("application.conf")
            val driverClassName = config.property("storage.driverClassName").getString()
            val url = config.property("storage.jdbcURL").getString()

            // Connects with the database
            database = Database.connect(url, driverClassName)

            // copy all users from users.json to a testFile.json
            val users = daoUsers.getAll()
            val usersJson = Json.encodeToJsonElement<List<User>>(users).toString()
            File(FILE_NAME).writeText(usersJson)

            // mock the repository class and mock the return file to be the test file
            mockkObject(daoUsers, recordPrivateCalls = true)
        }

        @AfterClass
        @JvmStatic
        fun afterTest() {
            unmockkAll()
            // reset test file
            File(FILE_NAME).writeText("[]")
        }
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
    fun getLoginPage() = testApplication {
        // Sending a GET request to "/login" endpoint
        val response = client.get("/login")
        // Asserting that the response status code is HttpStatusCode.OK (200)
        assertEquals(HttpStatusCode.OK, response.status)
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
        // Asserting that the redirected response body contains the expected message "Login is invalid!"
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

    // Test case to simulate a successful signup
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

        // Sending a POST request to "/signup" endpoint with form parameters
        val signupResponse = client.post("/signup") {
            // Setting the content type header to application
            header(HttpHeaders.ContentType, ContentType.Application.FormUrlEncoded.toString())
            // Setting the request body with form parameters encoded in form-url-encoded format
            setBody(formParameters.formUrlEncode())
        }

        val location = signupResponse.headers["Location"]
        println("Location header: $location")  // Debug logging

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

        // Sending a POST request to "/signup" endpoint with form parameters
        val signupResponse = client.post("/signup") {
            // Setting the content type header to application
            header(HttpHeaders.ContentType, ContentType.Application.FormUrlEncoded.toString())
            // Setting the request body with form parameters encoded in form-url-encoded format
            setBody(formParameters.formUrlEncode())
        }

        assertEquals(HttpStatusCode.OK, signupResponse.status)

        // Asserting that the Location header is not present, indicating that it's not a redirect
        assertFalse(signupResponse.headers.contains("Location"))
    }

    @Test
    fun accessProfilePageNotAuthenticated() = testApplication {
        val response = client.get("/profile") {
            // No session cookie added
        }

        // Assert that the response status code is HttpStatusCode.Found (302),
        // indicating a redirection to another page
        assertEquals(HttpStatusCode.Found, response.status)

        // Assert that the URL of the request is "/login", indicating redirection
        val url = response.headers["Location"]
        assertEquals("/login", url)
    }

    @Test
    fun accessProfilePageAuthenticated() = testApplication {
        val username = "testuser"

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
    fun deleteUserAccount() = testApplication {
        val username = "testuser"
        every { daoUsers.deleteItem(username) }

        // Send a DELETE request to "/user/account" endpoint with authenticated session
        val response = client.delete("/user/account") {
            cookie("auth-session", username)
        }

        // Assert that the response status code is HttpStatusCode.OK (200)
        assertEquals(HttpStatusCode.OK, response.status)
    }

    @Test
    fun updateUserUsername() = testApplication {
        val username = "testuser"
        val newUsername = "newtestuser"

        // Send a PUT request to "/user/username" endpoint with authenticated session
        val response = client.put("/user/username") {
            cookie("auth-session", username)
            setBody(FormDataContent(Parameters.build {
                append("newUsername", newUsername)
            }))
        }

        // Assert that the response status code is HttpStatusCode.OK (200)
        assertEquals(HttpStatusCode.Found, response.status)
    }

    @Test
    fun updateUserEmail() = testApplication {
        val username = "testuser"
        val newEmail = "newemail@example.com"

        // Send a PUT request to "/user/email" endpoint with authenticated session
        val response = client.put("/user/email") {
            cookie("auth-session", username)
            setBody(FormDataContent(Parameters.build {
                append("newEmail", newEmail)
            }))
        }

        // Assert that the response status code is HttpStatusCode.OK (200)
        assertEquals(HttpStatusCode.Found, response.status)
    }

    @Test
    fun `update user password`() = testApplication {
        val username = "testuser"
        val newPassword = "newpassword"

        every { daoUsers.updatePassword(username, newPassword) }

        val response = client.put("/user/password") {
            cookie("auth-session", username)
            setBody(FormDataContent(Parameters.build {
                append("newPassword", newPassword)
            }))
        }

        assertEquals(HttpStatusCode.Found, response.status)
    }

    @Test
    fun `update profile picture`() = testApplication {
        val username = "testuser"
        val imageData = byteArrayOf(0x01, 0x02, 0x03) // Example image data

        every { daoUsers.updateProfilePicture(username, any()) }

        val response = client.put("/user/profilepicture") {
            cookie("auth-session", username)
            setBody(MultiPartFormDataContent(formData {
                append("imageData", "image.jpg", ContentType.Image.JPEG) {
                   // writeBytes(imageData)
                }
            }))
        }

        assertEquals(HttpStatusCode.Found, response.status)
    }

    @Test
    fun `remove profile picture`() = testApplication {
        val username = "testuser"

        every { daoUsers.removeProfilePicture(username) }

        val response = client.delete("/user/profilepicture") {
            cookie("auth-session", username)
        }

        assertEquals(HttpStatusCode.Found, response.status)
    }

    @Test
    fun `get Hogwarts house`() = testApplication {
        val username = "testuser"
        val houseName = "Gryffindor" // Example house name

        every { daoUsers.getHouse(username) } returns houseName

        val response = client.get("/hogwards-house") {
            cookie("auth-session", username)
        }

        assertEquals(HttpStatusCode.OK, response.status)
    }

    @Test
    fun `get profile picture`() = testApplication {
        val username = "testuser"
        val mockImageContent = "static/img/house.png" // Example image content

        every { daoUsers.getProfilePictureData(username) } returns mockImageContent.toByteArray()

        val response = client.get("/profile-picture") {
            cookie("auth-session", username)
        }

        assertEquals(HttpStatusCode.OK, response.status)
    }

}

