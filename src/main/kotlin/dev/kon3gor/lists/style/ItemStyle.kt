package dev.kon3gor.lists.style

import kotlinx.css.*

val Style.item get() = className("item")

internal fun CssBuilder.itemStyle() = rule(Style.item) {
    display = Display.flex
    justifyContent = JustifyContent.spaceBetween
    alignItems = Align.start
    flexDirection = FlexDirection.row
    padding = Padding(2.vh)
    width = 100.pct
}

val Style.completedItem get() = className("completed-item")

internal fun CssBuilder.completedItemStyle() = rule(Style.completedItem) {
    display = Display.flex
    justifyContent = JustifyContent.spaceBetween
    alignItems = Align.start
    flexDirection = FlexDirection.row
    padding = Padding(2.vh)
    width = 100.pct

    opacity = 0.3
}
