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
    template: String, // The name of the template to be rendered
    model: Any? = null, // The data model to be passed to the template, default is null
    etag: String? = null, // The ETag for caching purposes, default is null
    contentType: ContentType = ContentType.Text.Html.withCharset(Charsets.UTF_8) // The content type of the response, default is HTML with UTF-8 charset
): Unit {
    // Retrieve the "HX-Request" header from the request and convert it to a Boolean. Default to false if the header is not present.
    val hxRequest = request.header("HX-Request")?.toBoolean() ?: false

    // Prepare the model to include the hxRequest variable. If the original model is a Map, add hxRequest to it.
    // Otherwise, create a new map with hxRequest and the original model.
    val modelWithHxRequest = if (model is Map<*, *>) {
        model + ("hxRequest" to hxRequest) // Add hxRequest to the existing model map
    } else {
        mapOf("hxRequest" to hxRequest, "model" to model) // Create a new map containing hxRequest and the original model
    }

    // Respond with the rendered FreeMarker template, passing the template name, modified model, etag, and content type.
    respond(FreeMarkerContent(template, modelWithHxRequest, etag, contentType))
}


