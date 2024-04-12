package net.grandcentrix.backend.repository;

import kotlinx.serialization.json.Json
import net.grandcentrix.backend.models.House
import java.io.File

class HouseManager: RepositoryManager<House,String, List<House>,House?> {

    companion object {
        val HouseManagerInstance: HouseManager = HouseManager()
    }

    override fun getAll(): List<House> {
        val housesFile = File("houses.json")
        if (!housesFile.exists()) {
            housesFile.createNewFile()
        }

        val fileText = housesFile.readText()
        if (fileText != "[]") {
            val housesList = Json.decodeFromString<List<House>>(fileText)

            return housesList
        }

        return listOf()
    }

    override fun getItem(name: String): House? = getAll().find { it.name == name }

    override fun deleteItem(name: String){
        TODO("Not yet implemented")
    }

    override fun addItem(item: House) {
        TODO("Not yet implemented")
    }

    override fun updateItem(item: House) {
        TODO("Not yet implemented")
    }

}
