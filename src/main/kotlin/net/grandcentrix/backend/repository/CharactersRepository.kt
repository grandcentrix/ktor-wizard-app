package net.grandcentrix.backend.repository

import net.grandcentrix.backend.models.Character
import net.grandcentrix.backend.plugins.api.APIRequesting.fetchCharacters

class CharactersRepository: RepositoryFacade<Character, Character?> {

    companion object {
        val CharactersRepositoryInstance: CharactersRepository = CharactersRepository()
    }

    override fun getAll(): List<Character> = fetchCharacters()

    override fun getItem(id: String): Character? = getAll().find { it.id == id }
}