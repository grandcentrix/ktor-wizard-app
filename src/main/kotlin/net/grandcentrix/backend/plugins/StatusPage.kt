package net.grandcentrix.backend.plugins

import io.ktor.server.application.*
import io.ktor.server.http.content.*
import io.ktor.server.plugins.statuspages.*
import io.ktor.server.routing.*

fun Application.configureStatusPage() {
    routing {
        staticResources("/static", "static")
    }

    install(StatusPages) {

    }
}