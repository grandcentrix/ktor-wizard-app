package net.grandcentrix.backend.plugins.api

import net.grandcentrix.backend.models.*
import net.grandcentrix.backend.plugins.api.APIRequesting.fetchBooks
import net.grandcentrix.backend.plugins.api.APIRequesting.fetchCharacters
import net.grandcentrix.backend.plugins.api.APIRequesting.fetchHouses
import net.grandcentrix.backend.plugins.api.APIRequesting.fetchMovies
import net.grandcentrix.backend.plugins.api.APIRequesting.fetchPotions
import net.grandcentrix.backend.plugins.api.APIRequesting.fetchSpells
import org.junit.Test
import kotlin.test.assertIs
import kotlin.test.assertNotNull

class APIRequestingTest {
    @Test
    fun testFetchBooks() {
        assertIs<List<Book>>(fetchBooks())
        assertNotNull(fetchBooks())
    }

    @Test
    fun testFetchHouses() {
        assertIs<List<House>>(fetchHouses())
        assertNotNull(fetchHouses())
    }

    @Test
    fun testFetchCharacters() {
        assertIs<List<Character>>(fetchCharacters())
        assertNotNull(fetchCharacters())
    }

    @Test
    fun testFetchMovies() {
        assertIs<List<Movie>>(fetchMovies())
        assertNotNull(fetchMovies())
    }

    @Test
    fun testFetchPotions() {
        assertIs<List<Potion>>(fetchPotions())
        assertNotNull(fetchPotions())
    }

    @Test
    fun testFetchSpells() {
        assertIs<List<Spell>>(fetchSpells())
        assertNotNull(fetchSpells())
    }
}