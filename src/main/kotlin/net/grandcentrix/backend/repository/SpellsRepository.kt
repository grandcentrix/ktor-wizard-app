package net.grandcentrix.backend.repository

import net.grandcentrix.backend.models.Spell

class SpellsRepository: RepositoryFacade<String, List<Spell>, Spell?> {

    companion object {
        val SpellsRepositoryInstance: SpellsRepository = SpellsRepository()
    }

    override fun getAll(): List<Spell> {
        TODO("Not yet implemented")
    }

    override fun getItem(name: String): Spell? {
        TODO("Not yet implemented")
    }
}