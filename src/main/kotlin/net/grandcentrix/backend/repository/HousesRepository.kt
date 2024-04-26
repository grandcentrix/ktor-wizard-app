package net.grandcentrix.backend.repository;

import net.grandcentrix.backend.models.House
import net.grandcentrix.backend.plugins.api.APIRequesting.fetchHouses

class HousesRepository: RepositoryFacade<House,House?> {

    companion object {
        val HousesRepositoryInstance: HousesRepository = HousesRepository()
    }

//    private var houses = listOf<House>()

//    private fun getFile() = File("houses.json")


    override fun getAll(): List<House> = fetchHouses()

    override fun getItem(id: String): House? = getAll().find { it.name == name }

//    override fun deleteItem(name: String){
//        TODO("Not yet implemented")
//    }

//    override fun addItem(item: House) {
//        houses = getAll() + item
//        val file = Json.encodeToJsonElement(houses).toString()
//        getFile().writeText(file)
//    }
//
//    override fun updateItem(item: House) {
//        TODO("Not yet implemented")
//    }

}
