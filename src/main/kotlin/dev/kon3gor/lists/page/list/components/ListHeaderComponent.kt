package dev.kon3gor.lists.page.list.components

import dev.kon3gor.lists.Component
import dev.kon3gor.lists.style.autoClass
import dev.kon3gor.lists.style.useCommonFont
import kotlinx.css.*
import kotlinx.html.FlowContent
import kotlinx.html.classes
import kotlinx.html.h1

fun FlowContent.listHeaderComponent(title: String) = ListHeaderComponent.render(this, ListHeaderComponent.Properties(title))

object ListHeaderComponent : Component<ListHeaderComponent.Properties>() {

    override val knownStyles = setOf(
        headerStyle
    )

    data class Properties(
        val title: String,
    )

    override fun FlowContent.render(properties: Properties) {
        h1 {
            classes = classSet(headerStyle)

            +properties.title
        }
    }
}

private val headerStyle by autoClass {
    fontSize = 4.em
    fontWeight = FontWeight.w700
    useCommonFont()
}