package net.grandcentrix.backend

import configureTemplating
import io.ktor.server.application.*
import net.grandcentrix.backend.plugins.configureRouting
import net.grandcentrix.backend.plugins.configureStatusPage

fun main(args: Array<String>) {
    io.ktor.server.netty.EngineMain.main(args)
}

fun Application.module() {
    configureRouting()
    configureStatusPage()
    configureTemplating()
}
