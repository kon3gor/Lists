package dev.kon3gor.lists.data

import dev.kon3gor.lists.model.ListInfo

interface ListStorage {
    suspend fun getList(slug: String): ListInfo
    suspend fun addItem(slug: String, title: String): Boolean
    suspend fun deleteItem(slug: String, id: String): Boolean
    suspend fun renameItem(slug: String, id: String, title: String): Boolean
    suspend fun completeItem(slug: String, id: String): Boolean
}