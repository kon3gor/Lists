package dev.kon3gor.lists.page.list

import dev.kon3gor.lists.AppPrefix
import dev.kon3gor.lists.model.ListItem
import dev.kon3gor.lists.page.list.components.addNewItemComponent
import dev.kon3gor.lists.page.list.components.listHeaderComponent
import dev.kon3gor.lists.style.*
import kotlinx.html.*

fun BODY.listPage(model: ListPage) {
    div {
        classes = classSet(Style.listPage)

        listHeaderComponent(model.header)
        addNewItemComponent(model.slug)

        items(model.items, model.slug)

        hr {
            classes = classSet(Style.listBreaker)
        }

        div {
            classes = classSet(Style.listContainer)

            model.completed.map { el ->
                item(el, true, model.slug)
            }
        }
    }
}

fun HtmlBlockTag.items(items: List<ListItem>, slug: String) {
    div {
        classes = classSet(Style.listContainer)

        items.map { el ->
            item(el, false, slug)
        }
    }
}

fun FlowContent.item(
    item: ListItem,
    completed: Boolean,
    slug: String,
) {
    div {
        classes = if (completed) {
            classSet(Style.completedItem)
        } else {
            classSet(Style.item)
        }

        id = "item_${item.id}"

        if (!completed) {
            editButton(slug, item.id)
        }

        div {
            classes = classSet(Style.itemTitle)
            id = "input${item.id}"

            attributes["contenteditable"] = ""
            attributes["hx-on:keypress"] = "onItemKeyPressed(event, \"${item.id}\")"
            attributes["hx-post"] = "${AppPrefix}/$slug/rename?id=${item.id}"
            attributes["hx-vals"] = "js:{title: getItemName(\"${item.id}\")}"
            attributes["hx-target"] = "#item_${item.id}"
            attributes["hx-trigger"] = "rename"
            attributes["hx-swap"] = "outerHTML"
            attributes["hx-ext"] = "json-enc"

            +item.title
        }

        div {
            classes = classSet(Style.itemButtonsContainer)

            if (completed) {
                completeButton()
                deleteButton()
            } else {
                completeButton(slug, item.id)
                deleteButton(slug, item.id)
            }
        }

    }
}

private fun DIV.completeButton(slug: String? = null, id: String? = null) {
    div {
        classes = classSet(itemButtonDoneStyle)

        if (slug != null) {
            requireNotNull(id)

            attributes["hx-post"] = "$AppPrefix/$slug/complete?id=$id"
            attributes["hx-target"] = Style.listPage.asRule
            attributes["hx-trigger"] = "click"
            attributes["hx-swap"] = "outerHTML"
        }

        span {
            classes = classSet(itemButtonIconStyle) + setOf("material-symbols-rounded")
            +"done"
        }
    }
}

private fun DIV.deleteButton(slug: String? = null, id: String? = null) {
    div {
        classes = classSet(itemButtonDeleteStyle)

        if (slug != null) {
            requireNotNull(id)

            attributes["hx-delete"] = "${AppPrefix}/$slug/delete?id=$id"
            attributes["hx-target"] = Style.listContainer.asRule
            attributes["hx-trigger"] = "click"
            attributes["hx-swap"] = "outerHTML"
        }

        span {
            classes = classSet(itemButtonIconStyle) + setOf("material-symbols-rounded")
            +"delete"
        }
    }
}

private fun DIV.userImage(url: String) {
    div {
        img(src = url) {
            classes = classSet(Style.userImage)
        }
    }
}

private fun DIV.editButton(slug: String, itemId: String) {
    div {
        id = "${itemId}_edit_btn"
        classes = classSet(Style.editButton)

        attributes["hx-on:click"] = "enableEditFor(\"$itemId\")"

        span {
            classes = setOf("material-symbols-rounded")
            +"edit"
        }

    }
}