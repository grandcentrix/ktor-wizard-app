package net.grandcentrix.backend

import configureTemplating
import io.ktor.server.application.*
import io.ktor.server.netty.*
import net.grandcentrix.backend.dao.DatabaseSingleton
import net.grandcentrix.backend.plugins.api.APIRequesting.fetchCharacterById
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

    //fun sad() = fetchCharacterById("2ac83f4d-c50e-4475-b09c-15e5a80da030")
   // println(sad())

}

