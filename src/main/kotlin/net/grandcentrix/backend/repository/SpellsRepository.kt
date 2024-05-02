package net.grandcentrix.backend.repository

import net.grandcentrix.backend.models.Spell

class SpellsRepository: RepositoryFacade<Spell, Spell?> {

    companion object {
        val SpellsRepositoryInstance: SpellsRepository = SpellsRepository()
    }

    override fun getAll(): List<Spell> {
        TODO("Not yet implemented")
    }

    override fun getItem(id: String): Spell? {
        TODO("Not yet implemented")
    }
}