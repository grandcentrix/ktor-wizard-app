package net.grandcentrix.backend.repository;

import net.grandcentrix.backend.models.House
import net.grandcentrix.backend.plugins.api.APIRequesting.fetchHouses

class HousesRepository: RepositoryFacade<House,House?> {

    companion object {
        val HousesRepositoryInstance: HousesRepository = HousesRepository()
    }

    override fun getAll(): List<House> = fetchHouses()

    override fun getItem(id: String): House? = getAll().find { it.id == id }

}
