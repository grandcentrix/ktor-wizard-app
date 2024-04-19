package net.grandcentrix.backend.repository;

import kotlinx.coroutines.runBlocking
import net.grandcentrix.backend.models.Book
import net.grandcentrix.backend.plugins.api.APIRequesting.fetchBooks

class BooksManager: ManagerFacade<Book,String, List<Book>, Book?> {

    companion object {
        val BooksManagerInstance: BooksManager = BooksManager()
    }

    private var books = listOf<Book>()

//    private fun getFile() = File("houses.json")


    override fun getAll(): List<Book> {
        var books: List<Book>
        runBlocking {
            books = fetchBooks()
        }
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
