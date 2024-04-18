package net.grandcentrix.backend.repository;

import kotlinx.serialization.json.Json
import kotlinx.serialization.json.encodeToJsonElement
import net.grandcentrix.backend.dao.FetchAPIData.fetchBooks
import net.grandcentrix.backend.models.Book
import net.grandcentrix.backend.models.House
import net.grandcentrix.backend.plugins.requestBooks
import java.io.File

class BooksManager: RepositoryManager<Book,String, List<Book>, Book?> {

    companion object {
        val BooksManagerInstance: BooksManager = BooksManager()
    }

    private var books = listOf<Book>()

//    private fun getFile() = File("houses.json")


    override fun getAll(): List<Book> {
        val books = fetchBooks()
        return books
    }

    override fun deleteItem(name: String) {
        TODO("Not yet implemented")
    }

    override fun addItem(item: Book) {
        TODO("Not yet implemented")
    }

    override fun updateItem(item: Book) {
        TODO("Not yet implemented")
    }

    override fun getItem(name: String): Book? {
        TODO("Not yet implemented")
    }
}
