package net.grandcentrix.backend.dao

import kotlinx.serialization.json.jsonArray
import kotlinx.serialization.json.jsonObject
import net.grandcentrix.backend.models.Book
import net.grandcentrix.backend.plugins.requestBooks

object FetchAPIData {
    suspend fun fetchBooks(): List<Book> {
        val resJson = requestBooks()
        val jsonData = resJson.jsonObject["data"]?.jsonArray
        val attributes = jsonData?.map {
            it.jsonObject["attributes"]
        }

        val books = attributes?.map { attribute ->
            val id = attribute?.jsonObject?.get("id").toString()
            val author = attribute?.jsonObject?.get("author").toString()
            val coverUrl = attribute?.jsonObject?.get("cover").toString()
            val pages = attribute?.jsonObject?.get("pages").toString().toInt()
            val releaseDate = attribute?.jsonObject?.get("release_date").toString()
            val summary = attribute?.jsonObject?.get("summary").toString()
            val slug = attribute?.jsonObject?.get("slug").toString()
            val title = attribute?.jsonObject?.get("title").toString()

            // create a book with the specific attributes from the json data and add to a list of books
            Book(id, author, coverUrl, pages, releaseDate, summary, slug, title)

        }?.toList() ?: emptyList()

        return books
    }
}