package dev.kon3gor.lists.routing

import dev.kon3gor.lists.AppContext
import dev.kon3gor.lists.AppPrefix
import dev.kon3gor.lists.style.create
import io.ktor.http.ContentType
import io.ktor.server.application.Application
import io.ktor.server.application.ApplicationCall
import io.ktor.server.application.call
import io.ktor.server.http.content.staticResources
import io.ktor.server.response.respondText
import io.ktor.server.routing.get
import io.ktor.server.routing.route
import io.ktor.server.routing.routing
import kotlinx.css.CssBuilder
import kotlin.jvm.Throws

fun Application.setupRouting(context: AppContext) = routing {
    route(AppPrefix) {
        staticResources("/assets", "assets")

        get("/style.css") {
            val css = CssBuilder().apply { create() }
            call.respondText(css.toString(), ContentType.Text.CSS)
        }


        route("/{name}") {
            listPage(context.storage)
            complete(context.storage)
            delete(context.storage)
            add(context.storage)
            rename(context.storage)
        }
    }
}

@Throws(IllegalArgumentException::class)
internal fun ApplicationCall.requireQueryParameter(name: String): String {
    return requireNotNull(request.queryParameters[name]) { "Missing or null query parameter: $name" }
}

@Throws(IllegalArgumentException::class)
internal fun ApplicationCall.requirePathParameter(name: String): String {
    return requireNotNull(parameters[name]) { "Missing or null path parameter: $name" }
}
