package dev.kon3gor.lists.data

import dev.kon3gor.lists.model.ListInfo
import dev.kon3gor.lists.model.ListItem
import kotlinx.coroutines.Dispatchers
import org.jetbrains.exposed.dao.id.IdTable
import org.jetbrains.exposed.dao.id.IntIdTable
import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.SqlExpressionBuilder.eq
import org.jetbrains.exposed.sql.javatime.timestamp
import org.jetbrains.exposed.sql.transactions.experimental.newSuspendedTransaction
import org.jetbrains.exposed.sql.transactions.transaction
import java.time.Instant

class PostgresListsStorage(
    private val database: Database,
) : ListStorage {

    object ListInfos : IdTable<String>() {
        override val id = varchar("slug", 10).entityId()
        val title = varchar("title", 50)

        override val primaryKey = PrimaryKey(id)
    }

    object ListItems : IntIdTable() {
        val title = varchar("title", 512)
        val completed = bool("completed")
        val list = reference("list", ListInfos)
        val created = timestamp("created")
    }

    init {
        transaction(database) {
            SchemaUtils.create(ListInfos, ListItems)
        }
    }

    override suspend fun getList(slug: String): ListInfo = dbQuery {
        val info = ListInfos.selectAll().where { ListInfos.id eq slug }.singleOrNull()
        if (info == null) {
            ListInfos.insert {
                it[id] = slug
                it[title] = slug

            }
            return@dbQuery ListInfo(slug, slug, emptyList(), emptyList())
        }

        val completed = mutableListOf<ListItem>()
        val items = mutableListOf<ListItem>()
        ListItems
            .innerJoin(ListInfos)
            .select(ListInfos.title, ListItems.id, ListItems.title, ListItems.completed)
            .where { ListInfos.id eq slug }
            .orderBy(ListItems.created, order = SortOrder.DESC)
            .forEach { result ->
                if (result[ListItems.completed]) {
                    completed.add(ListItem(result[ListItems.id].value.toString(), result[ListItems.title]))
                } else {
                    items.add(ListItem(result[ListItems.id].value.toString(), result[ListItems.title]))
                }
            }

        val title = info[ListInfos.title]
        ListInfo(slug, title, items, completed)
    }

    override suspend fun addItem(slug: String, title: String): Boolean = dbQuery {
        ListItems.insert {
            it[ListItems.title] = title
            it[list] = slug
            it[completed] = false
            it[created] = Instant.now()
        }

        true
    }

    override suspend fun deleteItem(slug: String, id: String): Boolean = dbQuery {
        ListItems.deleteWhere { ListItems.id eq id.toInt() }
        true
    }

    override suspend fun renameItem(slug: String, id: String, title: String): Boolean = dbQuery {
        ListItems.update({ ListItems.id eq id.toInt() }) {
            it[ListItems.title] = title
        }
        true
    }

    override suspend fun completeItem(slug: String, id: String): Boolean = dbQuery {
        ListItems.update({ ListItems.id eq id.toInt() }) {
            it[completed] = true
            it[created] = Instant.now()
        }
        true
    }

      private suspend fun <T> dbQuery(block: suspend () -> T) =
          newSuspendedTransaction(Dispatchers.IO, database) { block() }
}