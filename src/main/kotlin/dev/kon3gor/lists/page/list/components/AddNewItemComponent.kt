package dev.kon3gor.lists.page.list.components

import dev.kon3gor.lists.AppPrefix
import dev.kon3gor.lists.ColorPallet
import dev.kon3gor.lists.Component
import dev.kon3gor.lists.style.*
import kotlinx.css.*
import kotlinx.html.*

fun FlowContent.addNewItemComponent(slug: String) {
    AddNewItemComponent.render(this, AddNewItemComponent.Properties(slug))
}

object AddNewItemComponent : Component<AddNewItemComponent.Properties>() {

    data class Properties(
        val slug: String
    )

    override val knownStyles = setOf(
        addNewItemContainerOuterStyle,
        addNewItemContainerInnerStyle,
        addButtonContainerStyle,
        addButtonStyle,
        newItemInputStyle,
        newItemLineBreakerStyle,
    )

    override fun FlowContent.render(properties: Properties) {
        div {
            classes = classSet(addNewItemContainerOuterStyle)

            div {
                classes = classSet(addNewItemContainerInnerStyle)

                textInput {
                    id = "new-item-input"
                    classes = classSet(newItemInputStyle)

                    placeholder = "Edit me..."
                    name = "title"
                }

                div {
                    id = "new-item-button"
                    classes = classSet(addButtonContainerStyle)

                    attributes["hx-post"] = "$AppPrefix/${properties.slug}/add"
                    attributes["hx-target"] = Style.listContainer.asRule
                    attributes["hx-trigger"] = "click"
                    attributes["hx-include"] = "previous input"
                    attributes["hx-swap"] = "outerHTML"
                    attributes["hx-on:click"] = "clearInput()"
                    attributes["hx-ext"] = "json-enc"

                    span {
                        classes = classSet(addButtonStyle) + setOf("material-symbols-rounded")
                        style = "font-size:3vw;"

                        +"add"
                    }
                }
            }

            hr {
                classes = classSet(newItemLineBreakerStyle)
            }
        }
    }
}

private val addNewItemContainerOuterStyle by autoClass {
    display = Display.flex
    flexDirection = FlexDirection.column
    width = 80.vw
    justifyContent = JustifyContent.center
}

private val addNewItemContainerInnerStyle by autoClass {
    display = Display.flex
    flexDirection = FlexDirection.row
    justifyContent = JustifyContent.spaceBetween
    width = 100.pct
}

private val size = 4.vw
private val addButtonContainerStyle by autoClass {
    width = size
    height = size
    borderRadius = size / 3
    backgroundColor = ColorPallet.newButtonColor
    display = Display.flex
    justifyContent = JustifyContent.center
    alignItems = Align.center
    boxSizing = BoxSizing.borderBox
}

private val addButtonStyle by autoClass {
    color = ColorPallet.backgroundColor
}

private val newItemInputStyle by autoClass {
    color = ColorPallet.backgroundColor
    backgroundColor = Color.transparent
    border = Border.none
    color = ColorPallet.mainTextColor
    fontSize = 2.em
    outline = Outline.none
    width = 100.pct - size

    placeholder {
        color = ColorPallet.breakerColor
    }
}

private val newItemLineBreakerStyle by autoClass {
    color = ColorPallet.breakerColor
    width = 100.pct
    borderStyle = BorderStyle.solid
    borderRadius = 20.px
}
