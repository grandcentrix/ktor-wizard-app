package net.grandcentrix.backend.repository

import net.grandcentrix.backend.models.Movie
import net.grandcentrix.backend.plugins.api.APIRequesting.fetchMovies

class MoviesRepository: RepositoryFacade<Movie, Movie?> {

    companion object {
        val MoviesRepositoryInstance: MoviesRepository = MoviesRepository()
    }

    override fun getAll(): List<Movie> = fetchMovies()

    override fun getItem(id: String): Movie? = getAll().find { it.id == id }
}