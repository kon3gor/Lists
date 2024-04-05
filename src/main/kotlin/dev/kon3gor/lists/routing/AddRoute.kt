package dev.kon3gor.lists.routing

import dev.kon3gor.lists.data.ListStorage
import dev.kon3gor.lists.page.list.items
import io.ktor.server.application.call
import io.ktor.server.html.respondHtml
import io.ktor.server.request.receive
import io.ktor.server.routing.Route
import io.ktor.server.routing.post
import kotlinx.html.body
import kotlinx.serialization.Serializable

@Serializable
private data class AddRequest(val title: String)

internal fun Route.add(storage: ListStorage) = post("/add") {
    val body = call.receive<AddRequest>()
    val name = call.requirePathParameter("name")
    storage.addItem(name, body.title)

    val list = storage.getList(name)
    call.respondHtml {
        body {
            items(list.items, name)
        }
    }
}
