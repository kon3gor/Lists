package dev.kon3gor.lists

import dev.kon3gor.lists.routing.setupRouting
import io.ktor.http.HttpStatusCode
import io.ktor.serialization.kotlinx.json.json
import io.ktor.server.application.Application
import io.ktor.server.application.ApplicationCall
import io.ktor.server.application.install
import io.ktor.server.plugins.contentnegotiation.ContentNegotiation
import io.ktor.server.plugins.statuspages.StatusPages
import io.ktor.server.response.respond
import kotlinx.serialization.Serializable


const val AppPrefix = "/app/lists"

fun main(args: Array<String>): Unit = io.ktor.server.netty.EngineMain.main(args)

fun Application.module() {
    val storage = provideStorage()

    val context = AppContext(
        storage = storage
    )

    install(ContentNegotiation) {
        json()
    }

    install(StatusPages) {
        exception<IllegalArgumentException> { call, cause ->
            call.respond(
                status = HttpStatusCode.BadRequest,
                message = GenericResponse.BadRequest(cause.message ?: "Something bad is happening")
            )
        }
    }

    setupRouting(context)
}

internal sealed interface GenericResponse {
    val message: String

    @Serializable
    class BadRequest(override val message: String): GenericResponse
}
