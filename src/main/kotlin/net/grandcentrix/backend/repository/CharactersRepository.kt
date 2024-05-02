package net.grandcentrix.backend.repository

import net.grandcentrix.backend.models.Character

class CharactersRepository: RepositoryFacade<Character, Character?> {

    companion object {
        val CharactersRepositoryInstance: CharactersRepository = CharactersRepository()
    }

    override fun getAll(): List<Character> {
        TODO("Not yet implemented")
    }
    override fun getItem(id: String): Character? = getAll().find { it.id == id }
}