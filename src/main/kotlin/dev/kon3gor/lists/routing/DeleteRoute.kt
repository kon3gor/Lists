package dev.kon3gor.lists.routing

import dev.kon3gor.lists.data.ListStorage
import dev.kon3gor.lists.page.list.items
import io.ktor.server.application.call
import io.ktor.server.html.respondHtml
import io.ktor.server.routing.Route
import io.ktor.server.routing.delete
import kotlinx.html.body

internal fun Route.delete(storage: ListStorage) = delete("/delete") {
    val id = call.requireQueryParameter("id")
    val name = call.requirePathParameter("name")
    storage.deleteItem(name, id)

    val list = storage.getList(name)
    call.respondHtml {
        body {
            items(list.items, name)
        }
    }
}
