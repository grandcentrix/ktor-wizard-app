package net.grandcentrix.backend.repository

import net.grandcentrix.backend.repository.SpellsRepository.Companion.SpellsRepositoryInstance
import org.junit.Test
import kotlin.test.assertNotNull
import kotlin.test.assertTrue

class SpellsRepositoryTest {
    @Test
    fun testGetSpells() {
        val spells = SpellsRepositoryInstance.getAll()

        assertNotNull(spells)
        assertTrue { spells.isNotEmpty() }
    }

    @Test
    fun testGetSpell() {
//        val spell = SpellsRepositoryInstance.getItem("e4e30085-14df-4de2-8888-4bce50f50ea3")
//
//        assertNotNull(spell)
    }
}