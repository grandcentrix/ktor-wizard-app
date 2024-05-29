package net.grandcentrix.backend.repository

import net.grandcentrix.backend.repository.MoviesRepository.Companion.MoviesRepositoryInstance
import org.junit.Test
import kotlin.test.assertNotNull
import kotlin.test.assertTrue

class MoviesRepositoryTest {
    @Test
    fun testGetMovies() {
        val movies = MoviesRepositoryInstance.getAll()

        assertNotNull(movies)
        assertTrue { movies.isNotEmpty() }
    }

    @Test
    fun testGetMovie() {
//        val movie = MoviesRepositoryInstance.getItem("bcfa8e4d-654b-4830-8590-19b06f72825d")
//
//        assertNotNull(movie)
    }
}