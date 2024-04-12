package net.grandcentrix.backend

import configureTemplating
import io.ktor.server.application.*
import io.ktor.server.netty.*
import net.grandcentrix.backend.plugins.configureAuthentication
import net.grandcentrix.backend.plugins.configureRouting
import net.grandcentrix.backend.plugins.configureStatusPage

fun main(args: Array<String>) {
    EngineMain.main(args)
}

fun Application.module() {
    configureAuthentication()
    configureRouting()
    configureStatusPage()
    configureTemplating()
}
