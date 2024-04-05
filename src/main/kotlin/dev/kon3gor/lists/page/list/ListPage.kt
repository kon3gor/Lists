package dev.kon3gor.lists.page.list

import dev.kon3gor.lists.model.ListItem

data class ListPage(
    val slug: String,
    val header: String,
    val items: List<ListItem>,
    val completed: List<ListItem>,
    val userImg: String,
)
