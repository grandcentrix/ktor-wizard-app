package net.grandcentrix.backend.repository

import io.mockk.every
import io.mockk.spyk
import io.mockk.unmockkAll
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.encodeToJsonElement
import net.grandcentrix.backend.models.House
import net.grandcentrix.backend.repository.HouseRepository.Companion.HouseRepositoryInstance
import org.junit.Before
import org.junit.Test
import java.io.File
import kotlin.test.AfterTest
import kotlin.test.assertNotNull
import kotlin.test.assertTrue

class HouseRepositoryTest {
    companion object {
        private const val FILE_NAME = "src/main/resources/testFile.json"
    }

    private val houseManager = spyk(HouseRepositoryInstance, recordPrivateCalls = true)

    @Before
    fun beforeTests() {
        val house = House(
            1.toString(),
            "Gryffindor",
            "",
            "",
            "",
            "",
            "",
            "",
            heads = listOf(),
            traits = listOf()
        )
        val houses = listOf(house)
        val housesJson = Json.encodeToJsonElement(houses).toString()
        File(FILE_NAME).writeText(housesJson)

        every { houseManager["getFile"]() } returns File(FILE_NAME)
    }

    @AfterTest
    fun afterTest() {
        unmockkAll()
        File(FILE_NAME).writeText("[]")
    }

    @Test
    fun testGetHouses() {
        val houses = houseManager.getAll()

        assertNotNull(houses)
        assertTrue { houses.isNotEmpty() }
    }

    @Test
    fun testGetHouse() {
        val house = houseManager.getItem("Gryffindor")

        assertNotNull(house)
    }

    @Test
    fun addHouse() {
        val newHouse = House(
            2.toString(),
            "Ravenclaw",
            "",
            "",
            "",
            "",
            "",
            "",
            heads = listOf(),
            traits = listOf()
        )

        houseManager.addItem(newHouse)
        val createdUser = houseManager.getItem(newHouse.name)

        assertNotNull(createdUser)
    }
}