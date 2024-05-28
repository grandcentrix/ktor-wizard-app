package net.grandcentrix.backend.repository

import net.grandcentrix.backend.models.Potion
import net.grandcentrix.backend.plugins.api.APIRequesting.fetchPotions

class PotionsRepository: RepositoryFacade<Potion, Potion?> {

    companion object {
        val PotionsRepositoryInstance: PotionsRepository = PotionsRepository()
    }

    override fun getAll(): List<Potion> = fetchPotions()

    override fun getItem(id: String): Potion? = getAll().find { it.id == id }
}