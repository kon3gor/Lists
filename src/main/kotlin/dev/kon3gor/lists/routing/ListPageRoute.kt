package dev.kon3gor.lists.routing

import dev.kon3gor.lists.AppPrefix
import dev.kon3gor.lists.data.ListStorage
import dev.kon3gor.lists.page.list.ListPage
import dev.kon3gor.lists.page.list.listPage
import dev.kon3gor.lists.page.list.stubImage
import io.ktor.server.application.call
import io.ktor.server.html.respondHtml
import io.ktor.server.routing.Route
import io.ktor.server.routing.get
import kotlinx.html.body
import kotlinx.html.head
import kotlinx.html.script
import kotlinx.html.styleLink
import kotlinx.html.title

internal fun Route.listPage(storage: ListStorage) = get {
    val name = call.requirePathParameter("name")
    val list = storage.getList(name)
    call.respondHtml {
        head {
            title = name
            styleLink("$AppPrefix/style.css")
            styleLink("$AppPrefix/assets/global.css")
            styleLink("https://fonts.googleapis.com/css2?family=Material+Symbols+Rounded:opsz,wght,FILL,GRAD@20..48,100..700,0..1,-50..200")

            script("text/javascript", "$AppPrefix/assets/script.js") {}
            script(src = "https://unpkg.com/htmx.org@1.9.10") {
                integrity = "sha384-D1Kt99CQMDuVetoL1lrYwg5t+9QdHe7NLX/SoJYkXDFfX37iInKRy5xLSi8nO7UC"
                attributes["crossorigin"] = "anonymous"
            }
            script(src= "https://unpkg.com/htmx.org/dist/ext/json-enc.js") {}
        }

        body {
            listPage(ListPage(name, list.title, list.items, list.completed, stubImage))
        }
    }
}
