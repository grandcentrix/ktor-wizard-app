package net.grandcentrix.backend.repository

import net.grandcentrix.backend.models.Character

class CharactersRepository: RepositoryFacade<String, List<Character>, Character?> {

    companion object {
        val CharactersRepositoryInstance: CharactersRepository = CharactersRepository()
    }

    override fun getAll(): List<Character> {
        TODO("Not yet implemented")
    }
    override fun getItem(name: String): Character? {
        TODO("Not yet implemented")
    }
}