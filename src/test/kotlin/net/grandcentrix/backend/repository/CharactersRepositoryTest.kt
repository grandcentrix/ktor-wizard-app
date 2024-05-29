package net.grandcentrix.backend.repository;

import net.grandcentrix.backend.repository.CharactersRepository.Companion.CharactersRepositoryInstance
import org.junit.Test
import kotlin.test.assertNotNull
import kotlin.test.assertTrue

class CharactersRepositoryTest {
    @Test
    fun testGetCharacters() {
        val characters = CharactersRepositoryInstance.getAll()

        assertNotNull(characters)
        assertTrue { characters.isNotEmpty() }
    }

    @Test
    fun testGetCharacter() {
//        val character = CharactersRepositoryInstance.getItem("880e14ac-dcff-4e60-8138-bbcf788bfab8")
//
//        assertNotNull(character)
    }
}
