package dev.kon3gor.lists

import dev.kon3gor.lists.data.ListStorage
import dev.kon3gor.lists.data.ListStorageImpl
import dev.kon3gor.lists.data.PostgresListsStorage
import io.ktor.server.application.*
import org.jetbrains.exposed.sql.Database

internal fun Application.provideStorage(): ListStorage {
    return when (env) {
        Environment.Production -> productionStorage(provideDatabaseSecrets())
        Environment.Development -> localStorage(provideDatabaseSecrets())
        Environment.Dummy -> ListStorageImpl()
    }
}

private fun productionStorage(secrets: DatabaseSecrets): ListStorage {
    val database = Database.connect(
        url = "jdbc:postgresql://rc1b-xbgf5hdd5lr6y3su.mdb.yandexcloud.net:6432/lists?targetServerType=master&ssl=false",
        driver = "org.postgresql.Driver",
        user = secrets.user,
        password = secrets.password,
    )
    return PostgresListsStorage(database)
}

private fun localStorage(secrets: DatabaseSecrets): ListStorage {
    val database = Database.connect(
        url = "jdbc:postgresql://postgres:5432/lists",
        driver = "org.postgresql.Driver",
        user = secrets.user,
        password = secrets.password,
    )
    return PostgresListsStorage(database)
}
