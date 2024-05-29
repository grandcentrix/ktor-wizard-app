package net.grandcentrix.backend.repository

import net.grandcentrix.backend.repository.BooksRepository.Companion.BooksRepositoryInstance
import org.junit.Test
import kotlin.test.assertNotNull
import kotlin.test.assertTrue

class BooksRepositoryTest {
    @Test
    fun testGetBooks() {
        val books = BooksRepositoryInstance.getAll()

        assertNotNull(books)
        assertTrue { books.isNotEmpty() }
    }

    @Test
    fun testGetBook() {
//        val book = BooksRepositoryInstance.getItem("be86bc6e-d52e-4c46-86fe-76ce12fb99b2")
//
//        assertNotNull(book)
    }
}