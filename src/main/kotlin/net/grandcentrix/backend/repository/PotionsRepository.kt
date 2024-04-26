package net.grandcentrix.backend.repository

import net.grandcentrix.backend.models.Potion

class PotionsRepository: RepositoryFacade<Potion, Potion?> {

    companion object {
        val PotionsRepositoryInstance: PotionsRepository = PotionsRepository()
    }

    override fun getAll(): List<Potion> {
        TODO("Not yet implemented")
    }

    override fun getItem(id: String): Potion? {
        TODO("Not yet implemented")
    }
}