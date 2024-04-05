package dev.kon3gor.lists

import io.ktor.server.application.*

enum class Environment(private val slug: String) {
    Production("prod"), Development("dev"), Dummy("dummy");

    companion object {
        fun fromString(s: String): Environment {
            return values().first { it.slug == s }
        }
    }
}

val Application.env get() = Environment.fromString(environment.config.property("ktor.environment").getString())

data class DatabaseSecrets(
    val user: String,
    val password: String,
)

fun provideDatabaseSecrets(): DatabaseSecrets {
    val user = System.getenv("POSTGRES_USER")
    val password = System.getenv("POSTGRES_PASSWORD")

    return DatabaseSecrets(
        user = user,
        password = password,
    )
}