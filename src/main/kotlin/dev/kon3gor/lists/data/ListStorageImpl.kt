package dev.kon3gor.lists.data

import dev.kon3gor.lists.model.ListInfo
import dev.kon3gor.lists.model.ListItem
import kotlinx.css.li
import java.util.UUID

class ListStorageImpl : ListStorage {

    private val items = buildList {
        repeat(5) {
            add(ListItem("item_${it}_i","Watch it $it"))
        }
    }.toMutableList()

    private val completed = buildList {
        repeat(3) {
            add(ListItem("item_${it}_c","Watch it $it"))
        }
    }.toMutableList()

    private val lists = mutableMapOf<String, ListInfo>()

    override suspend fun getList(slug: String): ListInfo {
        return lists.getOrPut(slug) {
            ListInfo(
                slug = slug,
                title = slug,
                items = items,
                completed = completed,
            )
        }
    }

    override suspend fun addItem(slug: String, title: String): Boolean {
        val list = getList(slug)
        val id = UUID.randomUUID().toString()
        val newItem = ListItem(id, title)
        lists[slug] = list.copy(items = listOf(newItem) + list.items)
        return true
    }

    override suspend fun deleteItem(slug: String, id: String): Boolean {
        val list = getList(slug)
        lists[slug] = list.copy(items = list.items.filterNot { it.id == id })
        return true
    }

    override suspend fun renameItem(slug: String, id: String, title: String): Boolean {
        val list = getList(slug)
        val ind = requireNotNull(list.items.indexOfFirst { it.id == slug })
        val items = list.items.toMutableList()
        items[ind] = items[ind].copy(title = title)
        lists[slug] = list.copy(items = items)
        return true
    }

    override suspend fun completeItem(slug: String, id: String): Boolean {
        val list = getList(slug)
        val items = list.items.toMutableList()
        val item = requireNotNull(items.find { it.id == id })
        items.remove(item)
        lists[slug] = list.copy(items = items, completed = listOf(item) + list.completed)
        return true
    }
}
