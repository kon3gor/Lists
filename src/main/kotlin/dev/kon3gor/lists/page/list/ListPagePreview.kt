package dev.kon3gor.lists.page.list

import dev.kon3gor.lists.model.ListItem
import kotlinx.html.BODY

fun BODY.listPagePreview() {
    listPage(
        ListPage(
            header = "Idk list",
            items = buildList {
                repeat(6) {
                    add(ListItem("item_${it}_i", "Watch it $it"))
                }
            },
            completed = buildList {
                repeat(3) {
                    add(ListItem("item_${it}_c","Watch it $it"))
                }
            },
            userImg = stubImage,
            slug = "damn"
        )
    )
}

const val stubImage = "https://external-content.duckduckgo.com/iu/?u=https%3A%2F%2Ftse4.mm.bing.net%2Fth%3Fid%3DOIP.mnuiP2LZE8xODI5ZC1CZXAHaGp%26pid%3DApi&f=1&ipt=695e6057cc8665544d3d3b3518804a742909499dc9711f733dad37bb3ae09439&ipo=images"
