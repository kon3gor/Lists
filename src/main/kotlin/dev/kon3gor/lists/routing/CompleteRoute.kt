package dev.kon3gor.lists.routing

import dev.kon3gor.lists.data.ListStorage
import dev.kon3gor.lists.page.list.ListPage
import dev.kon3gor.lists.page.list.listPage
import dev.kon3gor.lists.page.list.stubImage
import io.ktor.server.application.call
import io.ktor.server.html.respondHtml
import io.ktor.server.routing.Route
import io.ktor.server.routing.post
import kotlinx.html.body

internal fun Route.complete(storage: ListStorage) = post("/complete") {
    val id = call.requireQueryParameter("id")
    val name = call.requirePathParameter("name")

    storage.completeItem(name, id)
    val list = storage.getList(name)

    call.respondHtml {
        body {
            listPage(ListPage(name, list.title, list.items, list.completed, stubImage))
        }
    }
}
