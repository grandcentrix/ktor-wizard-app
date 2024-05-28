package net.grandcentrix.backend.repository

import net.grandcentrix.backend.models.Spell
import net.grandcentrix.backend.plugins.api.APIRequesting.fetchSpells

class SpellsRepository: RepositoryFacade<Spell, Spell?> {

    companion object {
        val SpellsRepositoryInstance: SpellsRepository = SpellsRepository()
    }

    override fun getAll(): List<Spell> = fetchSpells()

    override fun getItem(id: String): Spell? = getAll().find { it.id == id }
}