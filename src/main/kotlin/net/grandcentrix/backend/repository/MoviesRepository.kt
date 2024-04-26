package net.grandcentrix.backend.repository

import net.grandcentrix.backend.models.Movie

class MoviesRepository: RepositoryFacade<Movie, Movie?> {

    companion object {
        val MoviesRepositoryInstance: MoviesRepository = MoviesRepository()
    }

    override fun getAll(): List<Movie> {
        TODO("Not yet implemented")
    }

    override fun getItem(id: String): Movie? {
        TODO("Not yet implemented")
    }
}