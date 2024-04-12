package net.grandcentrix.backend.repository;

import kotlinx.serialization.json.Json
import net.grandcentrix.backend.models.House
import net.grandcentrix.backend.models.User
import java.io.File

class HouseManager: RepositoryManager<String, List<House>,House?> {

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

    override fun deleteItem(item: String){
        TODO("Not yet implemented")
    }

    override fun addItem(item: String) {

    }

    override fun updateItem(item: String) {
        TODO("Not yet implemented")
    }

    override fun getItem(item: String): House? = getAll().find { it.name == item }



}
