import freemarker.cache.ClassTemplateLoader
import freemarker.core.HTMLOutputFormat
import io.ktor.server.application.*
import io.ktor.server.freemarker.*
import io.ktor.server.request.*
import io.ktor.server.response.*

fun Application.configureTemplating() {
    install(FreeMarker) {
        templateLoader = ClassTemplateLoader(this::class.java.classLoader, "templates")
        outputFormat = HTMLOutputFormat.INSTANCE
    }
}

suspend fun ApplicationCall.respondTemplating(
    template: String,
    model: Map<*, *>? = null
): Unit {
    val hxRequest = request.header("HX-Request")?.toBoolean() ?: false

    val modelWithHxRequest: Map<*, *> = when (model) {
        is Map<*, *> -> model + ("hxRequest" to hxRequest)
        null -> mapOf("hxRequest" to hxRequest)
        else -> throw IllegalArgumentException("Model must be a Map")
    }

    respond(FreeMarkerContent(template, modelWithHxRequest))
}

