package net.grandcentrix.backend.repository;

import kotlinx.coroutines.runBlocking
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.encodeToJsonElement
import net.grandcentrix.backend.models.House
import net.grandcentrix.backend.plugins.api.APIRequesting.fetchHouses
import java.io.File

class HouseManager: ManagerFacade<House,String, List<House>,House?> {

    companion object {
        val HouseManagerInstance: HouseManager = HouseManager()
    }

    private var houses = listOf<House>()

    private fun getFile() = File("houses.json")


    override fun getAll(): List<House> {
        var houses: List<House>
        runBlocking {
            houses = fetchHouses()
        }
        return houses
    }

    override fun getItem(name: String): House? = getAll().find { it.name == name }

    override fun deleteItem(name: String){
        TODO("Not yet implemented")
    }

    override fun addItem(item: House) {
        houses = getAll() + item
        val file = Json.encodeToJsonElement(houses).toString()
        getFile().writeText(file)
    }

    override fun updateItem(item: House) {
        TODO("Not yet implemented")
    }

}
