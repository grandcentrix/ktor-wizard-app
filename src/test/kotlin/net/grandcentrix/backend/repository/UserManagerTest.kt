package net.grandcentrix.backend.repository

import io.mockk.every
import io.mockk.spyk
import io.mockk.unmockkAll
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.encodeToJsonElement
import net.grandcentrix.backend.models.House
import net.grandcentrix.backend.models.User
import net.grandcentrix.backend.repository.UserManager.Companion.UserManagerInstance
import org.junit.Before
import org.junit.Test
import java.io.File
import kotlin.test.AfterTest
import kotlin.test.assertNotNull
import kotlin.test.assertTrue

class UserManagerTest {

    companion object {
        private const val FILE_NAME = "src/main/resources/testFile.json"
    }

    val userManager = spyk(UserManagerInstance, recordPrivateCalls = true)

    @Before
    fun beforeTests() {
        val user = User(
            1,
            "Person",
            "One",
            "personone@email.com",
            "personone",
            "pass",
            House(
                1,
                "Gryffindor",
                colors = listOf(),
                "",
                "",
                "",
                "",
                "",
                heads = listOf(),
                traits = listOf()
            ),
            favouriteItems = mutableListOf()
        )
        val users = listOf(user)
        val usersJson = Json.encodeToJsonElement(users).toString()
        File(FILE_NAME).writeText(usersJson)

        every { userManager.getFile() } returns File(FILE_NAME)
    }

    @AfterTest
    fun afterTest() {
        unmockkAll()
        File(FILE_NAME).writeText("[]")
    }

    @Test
    fun testGetUsers() {
        val users = UserManagerInstance.getAll()

        assertNotNull(users)
        assertTrue { users.isNotEmpty() }
    }

    @Test
    fun getUser() {
        val user = userManager.getItem("personone")

        assertNotNull(user)
    }

    @Test
    fun addUserWithoutHouse() {
        val newUser = User(
            2,
            "Person",
            "Two",
            "persontwo@email.com",
            "persontwo",
            "pass",
            null,
            favouriteItems = mutableListOf()
        )

        userManager.addItem(newUser)
        val createdUser = userManager.getItem(newUser.username)

        assertNotNull(createdUser)
    }

    @Test
    fun addUserWithoutFavourites() {
        val newUser = User(
            3,
            "Person",
            "Three",
            "personthree@email.com",
            "personthree",
            "pass",
            null
        )

        userManager.addItem(newUser)
        val createdUser = userManager.getItem(newUser.username)

        assertNotNull(createdUser)
    }
}