package net.grandcentrix.backend.repository

import net.grandcentrix.backend.repository.HousesRepository.Companion.HousesRepositoryInstance
import org.junit.Test
import kotlin.test.assertNotNull
import kotlin.test.assertTrue

class HousesRepositoryTest {

    @Test
    fun testGetHouses() {
        val houses = HousesRepositoryInstance.getAll()

        assertNotNull(houses)
        assertTrue { houses.isNotEmpty() }
    }

    @Test
    fun testGetHouse() {
        val house = HousesRepositoryInstance.getItem("0367baf3-1cb6-4baf-bede-48e17e1cd005")

        assertNotNull(house)
    }


}