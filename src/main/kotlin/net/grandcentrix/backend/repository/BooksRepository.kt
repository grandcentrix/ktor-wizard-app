package net.grandcentrix.backend.repository;

import net.grandcentrix.backend.models.Book
import net.grandcentrix.backend.plugins.api.APIRequesting.fetchBooks

class BooksRepository: RepositoryFacade<String, List<Book>, Book?> {

    companion object {
        val BooksRepositoryInstance: BooksRepository = BooksRepository()
    }

//    private var books = listOf<Book>()

    override fun getAll(): List<Book> = fetchBooks()

    override fun getItem(name: String): Book? {
        TODO("Not yet implemented")
    }
}
