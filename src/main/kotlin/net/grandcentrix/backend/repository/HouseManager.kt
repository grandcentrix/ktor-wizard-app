package net.grandcentrix.backend.repository;

import kotlinx.serialization.json.Json
import kotlinx.serialization.json.encodeToJsonElement
import net.grandcentrix.backend.models.House
import java.io.File

class HouseManager: RepositoryManager<House,String, List<House>,House?> {

    companion object {
        val HouseManagerInstance: HouseManager = HouseManager()
    }

    private var houses = listOf<House>()

    private fun getFile() = File("houses.json")


    override fun getAll(): List<House> {
        if (!getFile().exists()) {
            getFile().createNewFile()
        }

        val fileText = getFile().readText()
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
        houses = getAll() + item
        val file = Json.encodeToJsonElement(houses).toString()
        getFile().writeText(file)
    }

    override fun updateItem(item: House) {
        TODO("Not yet implemented")
    }

}
