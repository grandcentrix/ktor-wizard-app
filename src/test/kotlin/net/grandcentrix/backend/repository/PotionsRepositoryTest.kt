package net.grandcentrix.backend.repository

import net.grandcentrix.backend.repository.PotionsRepository.Companion.PotionsRepositoryInstance
import org.junit.Test
import kotlin.test.assertNotNull
import kotlin.test.assertTrue

class PotionsRepositoryTest {
    @Test
    fun testGetPotions() {
        val potions = PotionsRepositoryInstance.getAll()

        assertNotNull(potions)
        assertTrue { potions.isNotEmpty() }
    }

    @Test
    fun testGetPotion() {
//        val potion = PotionsRepositoryInstance.getItem("ff610acb-d24c-49e4-ae21-7ce123e5e41f")
//
//        assertNotNull(potion)
    }
}