package dev.kon3gor.lists.routing

import dev.kon3gor.lists.data.ListStorage
import dev.kon3gor.lists.model.ListItem
import dev.kon3gor.lists.page.list.item
import io.ktor.server.application.call
import io.ktor.server.html.respondHtml
import io.ktor.server.request.receive
import io.ktor.server.routing.Route
import io.ktor.server.routing.post
import kotlinx.html.body
import kotlinx.serialization.Serializable

@Serializable
private data class RenameRequest(
    val title: String,
)

internal fun Route.rename(storage: ListStorage) = post("/rename") {
    val body = call.receive<RenameRequest>()
    val id = call.requireQueryParameter("id")
    val slug = call.requirePathParameter("name")

    val title = body.title.removeSuffix("<br>")

    storage.renameItem(slug, id, title)

    call.respondHtml {
        body {
            item(ListItem(id, title), false, slug)
        }
    }
}
