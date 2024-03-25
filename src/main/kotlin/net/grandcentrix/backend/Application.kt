package net.grandcentrix.backend

import io.ktor.server.application.*
import net.grandcentrix.backend.plugins.configureRouting

fun main(args: Array<String>) {
    io.ktor.server.netty.EngineMain.main(args)
}

fun Application.module() {
    configureRouting()
}
