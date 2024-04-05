package dev.kon3gor.lists.style

import kotlinx.css.*

val Style.itemButtonsContainer get() = className("item-buttons-container")

internal fun CssBuilder.itemButtonsContainer() = rule(Style.itemButtonsContainer) {
    display = Display.flex
    flexDirection = FlexDirection.row
}
