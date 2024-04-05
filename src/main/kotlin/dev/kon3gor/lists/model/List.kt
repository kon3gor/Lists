package dev.kon3gor.lists.model

data class ListInfo(
    val slug: String,
    val title: String,
    val items: List<ListItem>,
    val completed: List<ListItem>
)