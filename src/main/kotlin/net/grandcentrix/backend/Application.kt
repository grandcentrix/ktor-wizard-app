package net.grandcentrix.backend

import configureTemplating
import io.ktor.server.application.*
import io.ktor.server.netty.*
import net.grandcentrix.backend.dao.DatabaseSingleton
import net.grandcentrix.backend.plugins.api.APIRequesting
import net.grandcentrix.backend.plugins.api.APIRequesting.getBookById
import net.grandcentrix.backend.plugins.configureAuthentication
import net.grandcentrix.backend.plugins.configureRouting
import net.grandcentrix.backend.plugins.configureStatusPage

fun main(args: Array<String>) {
    EngineMain.main(args)
}

fun Application.module() {
    DatabaseSingleton.init(environment.config)
    configureAuthentication()
    configureRouting()
    configureStatusPage()
    configureTemplating()

    val bookId = "be86bc6e-d52e-4c46-86fe-76ce12fb99b2" // replace with the actual ID of the book you want to fetch
    val book = APIRequesting.getBookById(bookId)
    println("Book by ID $bookId: $book")
}

