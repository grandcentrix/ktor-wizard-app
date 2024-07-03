import freemarker.cache.ClassTemplateLoader
import freemarker.core.HTMLOutputFormat
import io.ktor.http.*
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

suspend fun ApplicationCall.respondTemplates(
    template: String,
    model: Any? = null,
    etag: String? = null,
    contentType: ContentType = ContentType.Text.Html.withCharset(Charsets.UTF_8)
): Unit {
    val hxRequest = request.header("HX-Request")?.toBoolean() ?: false
    if (hxRequest) {
        // HX-Request is true, respond accordingly
        respond(FreeMarkerContent("errorttt.ftl", model, etag, contentType))
    } else {
        respond(FreeMarkerContent(template, model, etag, contentType))
    }
}