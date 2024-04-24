package net.grandcentrix.backend.repository;

import net.grandcentrix.backend.models.Book
import net.grandcentrix.backend.plugins.api.APIRequesting.fetchBooks

class BooksRepository: RepositoryFacade<Book,String, List<Book>, Book?> {

    companion object {
        val BooksRepositoryInstance: BooksRepository = BooksRepository()
    }

    private var books = listOf<Book>()

//    private fun getFile() = File("houses.json")


    override fun getAll(): List<Book> = fetchBooks()

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
